package com.tcc.cti.core.client.session;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.buffer.ByteMessageBuffer;
import com.tcc.cti.core.client.buffer.MessageBuffer;
import com.tcc.cti.core.client.connection.Connectionable;
import com.tcc.cti.core.client.heartbeat.HeartbeatKeepable;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.client.sequence.MemoryGeneratorSeq;
import com.tcc.cti.core.client.session.process.MessageProcessable;
import com.tcc.cti.core.message.MessageType;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

/**
 * 每个与CTI服务连接的用户都会创建于服务连接{@link Session},该方法封装了网络连接的复杂度。
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class Session implements Sessionable {
	private static final Logger logger = LoggerFactory.getLogger(Session.class);
	
	public static class Builder{
		private final OperatorKey _key;
		private final Connectionable _connection;
		private final MessageProcessable _messageProcess;
		private final HeartbeatKeepable _heartbeatKeep;
		private GeneratorSeq _generatorSeq ;		
		private int _heartbeatTimeout = 65 * 1000;
		private Charset _charset =Charset.forName("GBK");
		
		public Builder(OperatorKey key, Connectionable connection,
				MessageProcessable messageProcess,HeartbeatKeepable heartbeatKeep){
			
	    	_key = key;
	    	_connection = connection;
	    	_messageProcess = messageProcess;
	    	_heartbeatKeep = heartbeatKeep;
	    	_generatorSeq = new MemoryGeneratorSeq(
					_key.getCompanyId(), _key.getCompanyId());
	    }
		
		public Builder setGeneratorSeq(GeneratorSeq generatorSeq){
			_generatorSeq = generatorSeq;
			return this;
		}
		
		public Builder setHeartbeatTimeout(int timeout){
			_heartbeatTimeout = timeout;
			return this;
		}
		
		public Builder setCharset(Charset charset){
			_charset = charset;
			return this;
		}
		
		
		public Session build(){
			return new Session(_key, _connection, _messageProcess,
					_heartbeatKeep,_generatorSeq,_heartbeatTimeout,_charset);
		}
	}
	
	private final OperatorKey _key;
	private final Connectionable _conn; 
	private final MessageProcessable _messageProcess;
	private final HeartbeatKeepable _heartbeatKeep ;
	private final GeneratorSeq _generator;
	private final int _heartbeatTimeout;
	private final Charset _charset;
	private final MessageBuffer _messageBuffer;
	private final Object _monitor = new Object();
	
	private volatile Status _status = Status.New;
	private volatile long _lastTime = System.currentTimeMillis();
	
	private SocketChannel _channel;

	protected Session(OperatorKey operatorKey,Connectionable conn,
			MessageProcessable messageProcess,HeartbeatKeepable heartbeatKeep,
			GeneratorSeq generator,int heartbeatTimeout,Charset charset){
		
		_key = operatorKey;
		_conn = conn;
		_messageProcess = messageProcess;
		_heartbeatKeep = heartbeatKeep;
		_generator = generator;
		_charset = charset;
		_heartbeatTimeout = heartbeatTimeout;
		_messageBuffer = new ByteMessageBuffer(charset);
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
					_status = Status.Active;
					_channel =  _conn.connect(this);
				}catch(IOException e){
					logger.error("{} Session start is fail,error is {}",_key.toString(),e.getMessage());
					_status = Status.Close;
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
				_status = Status.Service;
				_heartbeatKeep.start(this);	
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
				_status = Status.Close;
				return ;
			}
			
			_status = Status.Close;
			_heartbeatKeep.shutdown();
			if(_channel != null && _channel.isOpen()){
				_channel.close();	
			}
		}
	}
	
	@Override
	public void append(byte[] bytes)throws InterruptedException{
		_messageBuffer.append(bytes);
		String m = _messageBuffer.next();
		if(StringUtils.isNotBlank(m)){
			_messageProcess.receiveProcess(this, m);
		}
	}
	
	@Override
	public void send(Requestable<? extends Response> request)throws IOException {
		synchronized (_monitor) {
			if(isNew()){
				start();
			}
		}
		if(isClose()){
			logger.debug("{} Send message,but closed",_key.toString());
			throw new IOException("Session already closed,not send message.");
		}
		if(isAccess(request.getMessageType())){
			_messageProcess.sendProcess(this, request, _generator, _charset);
			heartbeatTouch();
		}else{
			logger.debug("{} not access cti server.", _key.toString());
			throw new SessionAccessException(_key);
		}
	}
	
	private boolean isAccess(String messageType){
		boolean access = false;
		if(isService()){
			access = true;
		}
		if(isActive() && MessageType.Login.isRequest(messageType)){
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
		
		if(_status == Status.New ||_status == Status.Close){
			return _status;
		}
		
		//HeartBeat timeout
		long now = System.currentTimeMillis();
		int deff =(int)(now - _lastTime);
		if(deff > _heartbeatTimeout){
			_status = Status.Timeout;
		}
		
		return _status;
	}
	
	@Override
	public SocketChannel getSocketChannel() {
		return _channel;
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
