package com.tcc.cti.core.client;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.buffer.ByteMessageBuffer;
import com.tcc.cti.core.client.buffer.MessageBuffer;
import com.tcc.cti.core.client.monitor.HeartbeatKeepable;
import com.tcc.cti.core.client.monitor.ScheduledHeartbeatKeep;
import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.client.sequence.MemoryGeneratorSeq;
import com.tcc.cti.core.message.pool.CtiMessagePool;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 每个操作员与通过一个socket与cti服务器连接
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OperatorChannel {
	private static final Logger logger = LoggerFactory.getLogger(OperatorChannel.class);
	
	private final OperatorKey _operatorKey;
	private final GeneratorSeq _generator;
	private final SocketChannel _channel;
	private final MessageBuffer _messageBuffer;
	private final List<ReceiveHandler> _receiveHandlers;
	private final List<SendHandler> _sendHandlers;
	private final CtiMessagePool _pool;
	private final String _charset;
	private final Object _bufferMonitor = new Object();
	private final Thread _receiveThread = new Thread(new ReceiveRun());
	private volatile boolean _startReceive = false;
	private final Object _heartbeatMonitor = new Object();
	private HeartbeatKeepable _heartbeatKeep ;
	private volatile boolean _startHeartbeatKeep = false;
	
	public static class Builder{

		Builder(OperatorKey key,SocketChannel channel,CtiMessagePool pool){
	    	
	    }
		
	}
	

	public OperatorChannel(OperatorKey operatorKey,SocketChannel channel,
			List<ReceiveHandler> receiveHandlers,List<SendHandler> sendHandlers,
			CtiMessagePool pool,String charset) {

		this(operatorKey, channel,receiveHandlers, sendHandlers, pool,charset,
				new MemoryGeneratorSeq(operatorKey._companyId, operatorKey._opId));
	}

	public OperatorChannel(OperatorKey operatorKey, SocketChannel channel,
			List<ReceiveHandler> receiveHandlers,List<SendHandler> sendHandlers,
			CtiMessagePool pool,String charset,GeneratorSeq generator){
		
		_operatorKey = operatorKey;
		_channel = channel;
		_generator = generator;
		_messageBuffer = new ByteMessageBuffer(charset);
		_receiveHandlers = receiveHandlers;
		_sendHandlers = sendHandlers;
		_pool = pool;
		_charset = charset;
	}

	public SocketChannel getChannel() {
		return _channel;
	}

	public GeneratorSeq getGenerator() {
		return _generator;
	}

	public OperatorKey getOperatorKey() {
		return _operatorKey;
	}
	
	public void append(byte[] bytes)  {
		synchronized (_bufferMonitor) {
			_messageBuffer.append(bytes);
			_bufferMonitor.notifyAll();
		}
	}
	
	public boolean isOpen(){
		return _channel.isOpen();
	}
	
	public boolean isStartHeartbeatKeep(){
		return _startHeartbeatKeep;
	}
	
	public void send(RequestMessage message)throws ClientException {
		for(SendHandler handler : _sendHandlers){
			handler.send(_channel, message, _generator,_charset);
		}
	}
	
	public void startRecevie(){
		synchronized (_receiveThread) {
			if(_startReceive){
				return ;
			}
			_startReceive = true;
			_receiveThread.start();
		}
	}
	
	
	public void startHeartBeat(){
		synchronized (_heartbeatMonitor) {
			if(_startHeartbeatKeep){
				return ;
			}
			if(_heartbeatKeep == null){
				_heartbeatKeep = new ScheduledHeartbeatKeep(this);
				_heartbeatKeep.start();
				_startHeartbeatKeep = true;
			}
		}
	}
	
	public void close()throws ClientException{
		try{
			if(_startReceive){
				_receiveThread.interrupt();
			}
			if(_startHeartbeatKeep){
				_heartbeatKeep.shutdown();
			}
			if(_channel.isOpen()){
				_channel.close();
			}
		}catch(IOException e){
			throw new ClientException(e);
		}
	}
	
	public void setHeartbeatKeep(HeartbeatKeepable heartbeatKeep){
		_heartbeatKeep = heartbeatKeep;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_operatorKey == null) ? 0 : _operatorKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperatorChannel other = (OperatorChannel) obj;
		if (_operatorKey == null) {
			if (other._operatorKey != null)
				return false;
		} else if (!_operatorKey.equals(other._operatorKey))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OperatorChannel [_operatorKey=");
		builder.append(_operatorKey.toString());
		builder.append("]");
		return builder.toString();
	}
	
	private class ReceiveRun implements Runnable{

		@Override
		public void run() {
			while(true){
				try{
					
					if(Thread.interrupted()){
						break;
					}
					
					String m = null;
					synchronized (_bufferMonitor) {
						m = _messageBuffer.next();
						if(m == null){
							_bufferMonitor.wait();
						}
					}
					
					receiveHandle(m);	
				}catch(InterruptedException e){
					logger.error("Receive message interruped {}",e.getMessage());
				}
			}
		}
		
		private void receiveHandle(String m){
			
			if(StringUtils.isBlank(m)){
				return;
			}
			
			for(ReceiveHandler r : _receiveHandlers){
				try{
					r.receive(_pool,OperatorChannel.this, m);	
				}catch(ClientException e){
					logger.error("Receive client exception {}",e.getMessage());
				}
			}
		}
		
	}
}
