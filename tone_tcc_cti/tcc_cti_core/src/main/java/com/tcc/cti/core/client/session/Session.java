package com.tcc.cti.core.client.session;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.Configure;
import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.buffer.ByteMessageBuffer;
import com.tcc.cti.core.client.buffer.MessageBuffer;
import com.tcc.cti.core.client.connection.Connectionable;
import com.tcc.cti.core.client.connection.NioConnection;
import com.tcc.cti.core.client.heartbeat.HeartbeatKeepable;
import com.tcc.cti.core.client.heartbeat.ScheduledHeartbeatKeep;
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
import com.tcc.cti.core.client.session.task.MessageProcessTask;
import com.tcc.cti.core.message.MessageType;
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
		private HeartbeatKeepable _heartbeatKeep;
		private Connectionable _connection;
		
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
		
		public Builder setHeartbeatKeep(HeartbeatKeepable heartbeatKeep){
			_heartbeatKeep = heartbeatKeep;
			return this;
		}
		
		public Builder setConnection(Connectionable connection){
			_connection = connection;
			return this;
		}
		
		public Session build(){
			_receiveHandlers = _receiveHandlers != null ?
					_receiveHandlers : defaultReceiveHandlers();
			_sendHandlers = _sendHandlers != null ?
					_sendHandlers : defaultSendHandlers();
			_generatorSeq = _generatorSeq != null ?
					_generatorSeq : defaultGreneratorSeq();
			_connection = _connection != null ?
					_connection : defaultConnection();
			
			return new Session(_key, _connection, _pool,
					_receiveHandlers,_sendHandlers,_generatorSeq,
					_heartbeatKeep,_configure);
			
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
		
		private Connectionable defaultConnection(){
			return new NioConnection(
					_selector,_configure.getAddress(),
					_configure.getConnectionTimeout());
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
	
	private final MessageProcessTask _messageProcessTask ;
	private final Object _monitor = new Object();
	
	private volatile Status status = Status.New;
	private volatile long _lastTime = System.currentTimeMillis();
	
	private SocketChannel _channel;

	protected Session(OperatorKey operatorKey,Connectionable conn,CtiMessagePool pool,
			List<ReceiveHandler> receiveHandlers,List<SendHandler> sendHandlers,
			GeneratorSeq generator,HeartbeatKeepable heartbeatKeep,
			Configure configure){
		
		_key = operatorKey;
		_conn = conn;
		_generator = generator;
		_messageBuffer = new ByteMessageBuffer(configure.getCharset());
		_sendHandlers = sendHandlers;
		_heartbeatKeep = heartbeatKeep != null ? heartbeatKeep :
			defaultHeartbeatKeep(configure.getHeartbeatInitDelay(),configure.getHeartbeatDelay());
		_heartbeatTimeout = configure.getHeartbeatTimeout() * 1000;
		_messageProcessTask = new MessageProcessTask(
				pool,this,_messageBuffer,receiveHandlers);
		_charset = configure.getCharset();
	}
	
	private HeartbeatKeepable defaultHeartbeatKeep(int heartbeatInitDelay,int heartbeatDelay){
		
		return 	new ScheduledHeartbeatKeep(this,heartbeatInitDelay,heartbeatDelay);
	}
	
	private void startMessageProcess(MessageProcessTask task){
		Thread t = new Thread(task);
		t.start();
	}

	/**
	 * 链接服务端开始服务
	 * 
	 * @throws ClientException
	 */
	@Override
	public void start()throws IOException{
		synchronized (_monitor) {
			if(isNew()){
				logger.debug("{} Session is start",_key.toString());
				try{
					status = Status.Active;
					_channel =  _conn.connect(this);
					startMessageProcess(_messageProcessTask);	
				}catch(IOException e){
					logger.error("{} Session start is fail,error is {}",_key.toString(),e.getMessage());
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
			if(success && isActive()){
				status = Status.Service;
				_heartbeatKeep.start();	
				heartbeatTouch();
			}
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
			
			if(isNew()){
				status = Status.Close;
				return ;
			}
			
			status = Status.Close;
			_messageProcessTask.close();
			_heartbeatKeep.shutdown();
			if(_channel != null && _channel.isOpen()){
				_channel.close();	
			}
		}
	}
	
	@Override
	public void append(byte[] bytes)  {
		_messageBuffer.append(bytes);
		String m = _messageBuffer.next();
		if(StringUtils.isNotBlank(m)){
			
		}
	}
	
	@Override
	public void send(RequestMessage message)throws IOException {
		synchronized (_monitor) {
			if(isNew()){
				start();
			}
		}
		if(isClose()){
			logger.debug("{} Send message,but closed",_key.toString());
			throw new IOException("Session already closed,not send message.");
		}
		if(isAccess(message)){
			for(SendHandler handler : _sendHandlers){
				handler.send(_channel, message, _generator,_charset);
			}	
		}else{
			logger.debug("{} not access cti server.", _key.toString());
			throw new SessionAccessException(_key);
		}
	}
	
	private boolean isAccess(RequestMessage message){
		boolean access = false;
		if(isService()){
			access = true;
		}
		if(isActive() && MessageType.Login.isRequest(message.getMessageType())){
			access = true;
		}
		
		return access;
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
	public boolean isNew(){
		return getStatus() == Status.New;
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
