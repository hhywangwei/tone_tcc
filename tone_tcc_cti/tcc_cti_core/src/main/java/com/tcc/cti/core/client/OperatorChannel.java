package com.tcc.cti.core.client;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.buffer.ByteMessageBuffer;
import com.tcc.cti.core.client.buffer.MessageBuffer;
import com.tcc.cti.core.client.monitor.HeartbeatKeepable;
import com.tcc.cti.core.client.monitor.ScheduledHeartbeatKeep;
import com.tcc.cti.core.client.receive.CallReceiveHandler;
import com.tcc.cti.core.client.receive.CloseCallReceiveHandler;
import com.tcc.cti.core.client.receive.GroupMemberReceiveHandler;
import com.tcc.cti.core.client.receive.GroupReceiveHandler;
import com.tcc.cti.core.client.receive.LoginReceiveHandler;
import com.tcc.cti.core.client.receive.MonitorReceiveHandler;
import com.tcc.cti.core.client.receive.OutCallReceiveHandler;
import com.tcc.cti.core.client.receive.OutCallStateReceiveHandler;
import com.tcc.cti.core.client.receive.OwnReceiveHandler;
import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.receive.RecordReceiveHandler;
import com.tcc.cti.core.client.send.CallSendHandler;
import com.tcc.cti.core.client.send.GroupMemberSendHandler;
import com.tcc.cti.core.client.send.GroupSendHandler;
import com.tcc.cti.core.client.send.LoginSendHandler;
import com.tcc.cti.core.client.send.MonitorSendHandler;
import com.tcc.cti.core.client.send.OutCallCancelSendHandler;
import com.tcc.cti.core.client.send.OutCallSendHandler;
import com.tcc.cti.core.client.send.OwnSendHandler;
import com.tcc.cti.core.client.send.RecordSendHandler;
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
	private static final int DEFAULT_HEARTBEAT_INIT_DELAY = 0;
	private static final int DEFAULT_HEARTBEAT_DELAY = 20;
	private static final int DEFAULT_HEARTBEAT_TIMEOUT = 65;
	
	
	public static class Builder{
		private final OperatorKey _key;
		private final SocketChannel _channel;
		private final CtiMessagePool _pool;
		private List<ReceiveHandler> _receiveHandlers;
		private List<SendHandler> _sendHandlers;
		private GeneratorSeq _generatorSeq ;
		private String _charset = "UTF-8";
		private ScheduledExecutorService _executorService;
		private int _heartbeatInitDelay = DEFAULT_HEARTBEAT_INIT_DELAY;
		private int _heartbeatDelay = DEFAULT_HEARTBEAT_DELAY;
		private int _heartbeatTimeout = DEFAULT_HEARTBEAT_TIMEOUT; 

		
		public Builder(OperatorKey key,SocketChannel channel,CtiMessagePool pool){
	    	_key = key;
	    	_channel = channel;
	    	_pool = pool;
	    	_generatorSeq = new MemoryGeneratorSeq(key._companyId, key._opId);
	    }
		
		public Builder setReceiveHandlers(List<ReceiveHandler> receiveHandlers){
			_receiveHandlers = receiveHandlers;
			return this;
		}
		
		public Builder setSendHandlers(List<SendHandler> sendHandlers){
			_sendHandlers = sendHandlers;
			return this;
		}
		
		public Builder setCharset(String charset){
			_charset = charset;
			return this;
		}
		
		public Builder setGeneratorSeq(GeneratorSeq generatorSeq){
			_generatorSeq = generatorSeq;
			return this;
		}
		
		public Builder setScheduledExecutorService(ScheduledExecutorService executorService){
			_executorService = executorService;
			return this;
		}
		
		public Builder setHeartbeatInitDelay(int initDelay){
			_heartbeatInitDelay = initDelay;
			return this;
		}
		
		public Builder setHeartbeatDelay(int delay){
			_heartbeatDelay = delay;
			return this;
		}
		
		public Builder setHeartbeatTimeout(int timeout){
			_heartbeatTimeout = timeout;
			return this;
		}
		
		public OperatorChannel build(){
			_receiveHandlers = _receiveHandlers != null ?
					_receiveHandlers : defaultReceiveHandlers();
			_sendHandlers = _sendHandlers != null ?
					_sendHandlers : defaultSendHandlers();
			
			return new OperatorChannel(_key,_channel,_pool,_receiveHandlers,
					_sendHandlers,_generatorSeq,_charset,_executorService,
					_heartbeatInitDelay,_heartbeatDelay,_heartbeatTimeout);
			
		}
		
		private List<ReceiveHandler> defaultReceiveHandlers(){
			List<ReceiveHandler> handlers = new ArrayList<>();
			
			handlers.add(new LoginReceiveHandler());
			handlers.add(new OwnReceiveHandler());
			handlers.add(new GroupMemberReceiveHandler());
			handlers.add(new GroupReceiveHandler());
			handlers.add(new MonitorReceiveHandler());
			handlers.add(new OutCallReceiveHandler());
			handlers.add(new OutCallStateReceiveHandler());
			handlers.add(new CallReceiveHandler());
			handlers.add(new CloseCallReceiveHandler());
			handlers.add(new RecordReceiveHandler());
			
			return handlers;
		}
		
		private List<SendHandler> defaultSendHandlers(){
			List<SendHandler> handlers = new ArrayList<>();
			
			handlers.add(new LoginSendHandler());
			handlers.add(new OwnSendHandler());
			handlers.add(new GroupMemberSendHandler());
			handlers.add(new GroupSendHandler());
			handlers.add(new MonitorSendHandler());
			handlers.add(new OutCallSendHandler());
			handlers.add(new OutCallCancelSendHandler());
			handlers.add(new CallSendHandler());
			handlers.add(new RecordSendHandler());
			
			return handlers;
		}
		
	}
	
	private final OperatorKey _key;
	private final SocketChannel _channel;
	private final CtiMessagePool _pool;
	private final List<ReceiveHandler> _receiveHandlers;
	private final List<SendHandler> _sendHandlers;
	private final GeneratorSeq _generator;
	private final String _charset;
	private final HeartbeatKeepable _heartbeatKeep ;
	private final int _heartbeatTimeout;
	private final MessageBuffer _messageBuffer;
	
	private final Object _bufferMonitor = new Object();
	private final Thread _receiveThread ;
	private final Object _monitor = new Object();
	private volatile boolean _start = false;
	private volatile boolean _connecting = false;
	private volatile boolean _loginSuccessful = false;
	private volatile long _lastHeartbeanTime = System.currentTimeMillis();
	

	protected OperatorChannel(OperatorKey operatorKey,SocketChannel channel,
			CtiMessagePool pool,List<ReceiveHandler> receiveHandlers,List<SendHandler> sendHandlers,
			GeneratorSeq generator,String charset,ScheduledExecutorService executorService,
			int heartbeatInitDelay,int heartbeatDelay,int heartbeatTimeout){
		
		_key = operatorKey;
		_channel = channel;
		_generator = generator;
		_messageBuffer = new ByteMessageBuffer(charset);
		_receiveHandlers = receiveHandlers;
		_sendHandlers = sendHandlers;
		_pool = pool;
		_charset = charset;
		_heartbeatKeep = initHeartbeatKeep(
				executorService,heartbeatInitDelay,heartbeatDelay);
		_heartbeatTimeout = heartbeatTimeout * 1000;
		_receiveThread = new Thread(new ReceiveRun());
	}
	
	private HeartbeatKeepable initHeartbeatKeep(ScheduledExecutorService executorService,
			int heartbeatInitDelay,int heartbeatDelay){
		
		return 	new ScheduledHeartbeatKeep(this,
				executorService,heartbeatInitDelay,heartbeatDelay);
	}

	public void startConnection(){
		_connecting = true;
	}
	
	public void finishConnection(){
		_connecting = false;
	}
	
	public boolean isConnecting(){
		return _connecting;
	}
	
	public void start(){
		synchronized (_monitor) {
			if(_start){
				return ;
			}
			_start = true;
			_receiveThread.start();
		}
	}
	
	public boolean isStart(){
		return _start;
	}
	
	public boolean isStartHeartbeatKeep(){
		return _loginSuccessful;
	}
	
	public boolean isOffline(){
		long now = System.currentTimeMillis();
		int deff =(int)(now - _lastHeartbeanTime);
		return deff > _heartbeatTimeout;
	}
	
	public void heartbeatTouch(){
		_lastHeartbeanTime = System.currentTimeMillis();
	}
	
	public void login(boolean success){
		synchronized (_monitor) {
			_loginSuccessful = success;
			if(!_loginSuccessful){
				return ;
			}
			_heartbeatKeep.start();
		}
	}
	
	public void close()throws ClientException{
		synchronized (_monitor) {
			try{
				if(_start){
					_receiveThread.interrupt();
				}
				if(_loginSuccessful){
					_heartbeatKeep.shutdown();
				}
				if(_channel.isOpen()){
					_channel.close();
				}
			}catch(IOException e){
				throw new ClientException(e);
			}			
		}
	}
	
	public void append(byte[] bytes)  {
		synchronized (_bufferMonitor) {
			_messageBuffer.append(bytes);
			_bufferMonitor.notifyAll();
		}
	}
	
	public void send(RequestMessage message)throws ClientException {
		for(SendHandler handler : _sendHandlers){
			handler.send(_channel, message, _generator,_charset);
		}
	}
	
	public SocketChannel getChannel() {
		return _channel;
	}

	public GeneratorSeq getGenerator() {
		return _generator;
	}

	public OperatorKey getOperatorKey() {
		return _key;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_key == null) ? 0 : _key.hashCode());
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
		if (_key == null) {
			if (other._key != null)
				return false;
		} else if (!_key.equals(other._key))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OperatorChannel [_operatorKey=");
		builder.append(_key.toString());
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
