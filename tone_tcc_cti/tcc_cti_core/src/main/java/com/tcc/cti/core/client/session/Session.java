package com.tcc.cti.core.client.session;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.client.Configure;
import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.buffer.ByteMessageBuffer;
import com.tcc.cti.core.client.buffer.MessageBuffer;
import com.tcc.cti.core.client.connection.Connectionable;
import com.tcc.cti.core.client.connection.NioConnection;
import com.tcc.cti.core.client.monitor.HeartbeatKeepable;
import com.tcc.cti.core.client.monitor.ScheduledHeartbeatKeep;
import com.tcc.cti.core.client.receive.CallReceiveHandler;
import com.tcc.cti.core.client.receive.CloseCallReceiveHandler;
import com.tcc.cti.core.client.receive.GroupMemberReceiveHandler;
import com.tcc.cti.core.client.receive.GroupReceiveHandler;
import com.tcc.cti.core.client.receive.HeartbeatReceiveHandler;
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
import com.tcc.cti.core.client.send.HeartbeatSendHandler;
import com.tcc.cti.core.client.send.LoginSendHandler;
import com.tcc.cti.core.client.send.MonitorSendHandler;
import com.tcc.cti.core.client.send.OutCallCancelSendHandler;
import com.tcc.cti.core.client.send.OutCallSendHandler;
import com.tcc.cti.core.client.send.OwnSendHandler;
import com.tcc.cti.core.client.send.RecordSendHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.client.sequence.MemoryGeneratorSeq;
import com.tcc.cti.core.client.task.MessageProcessTask;
import com.tcc.cti.core.message.pool.CtiMessagePool;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 每个操作员与通过一个socket与cti服务器连接
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class Session implements Sessionable {
	private static final Logger logger = LoggerFactory.getLogger(Session.class);
	
	public static class Builder{
		private final OperatorKey _key;
		private final CtiMessagePool _pool;
		private final Selector _selector;
		private final Configure _configure;
		private List<ReceiveHandler> _receiveHandlers;
		private List<SendHandler> _sendHandlers;
		private GeneratorSeq _generatorSeq ;
		private ScheduledExecutorService _executorService;

		
		public Builder(OperatorKey key, Selector selector,Configure configure, CtiMessagePool pool){
	    	_key = key;
	    	_selector = selector;
	    	_configure = configure;
	    	_pool = pool;
	    }
		
		public Builder setReceiveHandlers(List<ReceiveHandler> receiveHandlers){
			_receiveHandlers = receiveHandlers;
			return this;
		}
		
		public Builder setSendHandlers(List<SendHandler> sendHandlers){
			_sendHandlers = sendHandlers;
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
		
		public Session build(){
			_receiveHandlers = _receiveHandlers != null ?
					_receiveHandlers : defaultReceiveHandlers();
			_sendHandlers = _sendHandlers != null ?
					_sendHandlers : defaultSendHandlers();
			_generatorSeq = _generatorSeq != null ?
					_generatorSeq : defaultGreneratorSeq();
			
			Connectionable conn = new NioConnection(
					_selector,_configure.getAddress(),_configure.getConnectionTimeout());
			return new Session(_key,conn,_pool,
					_receiveHandlers,_sendHandlers,_generatorSeq,
					_executorService,_configure);
			
		}
		
		private List<ReceiveHandler> defaultReceiveHandlers(){
			List<ReceiveHandler> handlers = new ArrayList<>();

			handlers.add(new HeartbeatReceiveHandler());
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
			
			handlers.add(new HeartbeatSendHandler());
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
		
		private GeneratorSeq defaultGreneratorSeq(){
			return new MemoryGeneratorSeq(
					_key.getCompanyId(), _key.getCompanyId());
		}
	}
	
	private final OperatorKey _key;
	private final Connectionable _conn; 
	private final List<SendHandler> _sendHandlers;
	private final GeneratorSeq _generator;
	private final HeartbeatKeepable _heartbeatKeep ;
	private final int _heartbeatTimeout;
	private final MessageBuffer _messageBuffer;
	private final Charset _charset;
	
	private final Thread _messageProcessThread ;
	private final Object _monitor = new Object();
	
	private volatile Status status = Status.New;
	private volatile long _lastTime = System.currentTimeMillis();
	
	private SocketChannel _channel;

	protected Session(OperatorKey operatorKey,Connectionable conn,CtiMessagePool pool,
			List<ReceiveHandler> receiveHandlers,List<SendHandler> sendHandlers,
			GeneratorSeq generator,ScheduledExecutorService executorService,
			Configure configure){
		
		_key = operatorKey;
		_conn = conn;
		_generator = generator;
		_messageBuffer = new ByteMessageBuffer(configure.getCharset());
		_sendHandlers = sendHandlers;
		_heartbeatKeep = initHeartbeatKeep(
				executorService,configure.getHeartbeatInitDelay(),configure.getHeartbeatDelay());
		_heartbeatTimeout = configure.getHeartbeatTimeout() * 1000;
		_messageProcessThread = new Thread(new MessageProcessTask(
				pool,this,_messageBuffer,receiveHandlers));
		_charset = configure.getCharset();
	}
	
	private HeartbeatKeepable initHeartbeatKeep(ScheduledExecutorService executorService,
			int heartbeatInitDelay,int heartbeatDelay){
		
		return 	new ScheduledHeartbeatKeep(this,
				executorService,heartbeatInitDelay,heartbeatDelay);
	}

	/**
	 * 链接服务端开始服务
	 * 
	 * @throws ClientException
	 */
	@Override
	public void start()throws IOException{
		synchronized (_monitor) {
			Status status = getStatus();
			if(status == Status.New){
				logger.debug("{} Session is start",_key.toString());
				try{
					status = Status.Active;
					_channel =  _conn.connect(this);
					_messageProcessThread.start();	
				}catch(IOException e){
					logger.error("{} Session start is fail,error is {}",e.getMessage());
					status = Status.Close;
					throw e;
				}
			}
		}
	}
	
	/**
	 * 登录是否成功成功开始心跳
	 * 
	 * @param success
	 */
	@Override
	public void login(boolean success){
		synchronized (_monitor) {
			if(success){
				status = Status.Service;
				_heartbeatKeep.start();	
			}
			heartbeatTouch();
		}
	}
	
	@Override
	public void heartbeatTouch(){
		_lastTime = System.currentTimeMillis();
	}
	
	@Override
	public void close()throws IOException{
		synchronized (_monitor) {
			if(isClose()){
				return ;
			}
			
			if(isService() || isTimeout()){
				_heartbeatKeep.shutdown();
			}
			
			if(isActive()){
				_channel.close();
				_messageProcessThread.interrupt();
			}
		}
	}
	
	@Override
	public void append(byte[] bytes)  {
		_messageBuffer.append(bytes);
	}
	
	@Override
	public void send(RequestMessage message)throws IOException {
		if(!isActive()){
			throw new RuntimeException("Not start ");
		}
		if(isClose()){
			throw new RuntimeException("Already close ");
		}
		for(SendHandler handler : _sendHandlers){
			handler.send(_channel, message, _generator,_charset);
		}
	}
	
	@Override
	public OperatorKey getOperatorKey() {
		return _key;
	}
	
	@Override
	public Status getStatus(){
		
		if(status == Status.New ||status == Status.Close){
			return status;
		}
		
		//HeartBeat timeout
		long now = System.currentTimeMillis();
		int deff =(int)(now - _lastTime);
		if(deff > _heartbeatTimeout){
			status = Status.Timeout;
		}
		
		return status;
	}
	
	@Override
	public boolean isActive(){
		return getStatus() == Status.Active ;
	}
	
	@Override
	public boolean isService(){
		return getStatus() == Status.Service;
	}
	
	@Override
	public boolean isClose(){
		return getStatus() == Status.Close;
	}
	
	@Override
	public boolean isTimeout(){
		return getStatus() == Status.Timeout;
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
		Session other = (Session) obj;
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
}
