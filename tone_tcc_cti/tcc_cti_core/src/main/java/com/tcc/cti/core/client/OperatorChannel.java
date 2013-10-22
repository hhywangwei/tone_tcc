package com.tcc.cti.core.client;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tcc.cti.core.client.buffer.ByteMessageBuffer;
import com.tcc.cti.core.client.buffer.MessageBuffer;
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
	private final OperatorKey _operatorKey;
	private final GeneratorSeq _generator;
	private final SocketChannel _channel;
	private final MessageBuffer _messageBuffer;
	private final List<ReceiveHandler> _receiveHandlers;
	private final List<SendHandler> _sendHandlers;
	private final CtiMessagePool _pool;
	private final String _charset;
	private final Object _monitor = new Object();
	private volatile int _bufferCount = 0;
	private volatile boolean _startHeartBeat = false;
	

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
		synchronized (_monitor) {
			_bufferCount++;
			_messageBuffer.append(bytes);
			_monitor.notifyAll();
		}
	}
	
	public String next(){
		return _messageBuffer.next();
	}
	
	public boolean isOpen(){
		return _channel.isOpen();
	}
	
	public boolean isStartHeartBeat(){
		return _startHeartBeat;
	}
	
	public void send(RequestMessage message)throws ClientException {
		for(SendHandler handler : _sendHandlers){
			handler.send(_channel, message, _generator,_charset);
		}
	}
	
	public void startRecevie()throws InterruptedException,ClientException{
		while(true){
			String m = null;
			synchronized (_monitor) {
				if(_bufferCount == 0){
					_monitor.wait();
				}
				m = _messageBuffer.next();
				_bufferCount--;
			}
			if(StringUtils.isNotBlank(m)){
				for(ReceiveHandler r : _receiveHandlers){
					r.receive(_pool, this, m);
				}
			}
		}
	}
	
	
	public void startHeartBeat(){
		
	}
	
	public void close()throws ClientException{
		try{
			if(_channel.isOpen()){
				_channel.close();
			}
		}catch(IOException e){
			throw new ClientException(e);
		}
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
}
