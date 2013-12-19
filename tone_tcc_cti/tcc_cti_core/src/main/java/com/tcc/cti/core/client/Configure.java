package com.tcc.cti.core.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * CTI服务器配置信息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei《/a>
 */
public class Configure {
	
	public static class Builder {
		private static final int HEART_POOL_SIZE = 3;
		private static final int DEFAULT_HEARTBEAT_INIT_DELAY = 0;
		private static final int DEFAULT_HEARTBEAT_DELAY = 20;
		private static final int DEFAULT_HEARTBEAT_TIMEOUT = 65 * 1000;
		private static final int DEFAULT_CONNECTION_TIMEOUT = 30 * 1000;
		private static final int DEFAULT_MAX_OPERATOR = 256;
		private static final Charset DEFAULT_CHARSET = Charset.forName("GBK");
		
		private final String _host;
		private final int _port;
		private int _heartPoolSize = HEART_POOL_SIZE;
		private int _heartbeatInitDelay = DEFAULT_HEARTBEAT_INIT_DELAY;
		private int _heartbeatDelay = DEFAULT_HEARTBEAT_DELAY;
		private int _heartbeatTimeout = DEFAULT_HEARTBEAT_TIMEOUT; 
		private int _connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
		private int _maxOperator = DEFAULT_MAX_OPERATOR;
		private  Charset _charset = DEFAULT_CHARSET;
		
		public Builder(String host,int port){
			this._host = host;
			this._port = port;
		}
		
		public Builder setHeartbeatInitDelay(int delay){
			if(delay < 0){
				throw new IllegalArgumentException("Heartbeat delay must >= 0");
			}
			_heartbeatInitDelay = delay;
			return this;
		}
		
		public Builder setHeartbeatDelay(int delay){
			if(delay <= 0){
				throw new IllegalArgumentException("Heartbeat delay must > 0");
			}
			_heartbeatDelay = delay;
			return this;
		}
		
		public Builder setHeartbeatTimeout(int timeout){
			if(timeout <= 0){
				throw new IllegalArgumentException("Heartbeat timeOut must > 0");
			}
			_heartbeatTimeout = timeout * 1000;
			return this;
		}
		
		public Builder setHeartPoolSize(int size){
			if(size <= 0){
				throw new IllegalArgumentException("Heart pool size must > 0");
			}
			_heartPoolSize = size;
			return this;
		}
		
		public Builder setConnectionTimeOut(int timeout){
			if(timeout <= 0){
				throw new IllegalArgumentException("Connection timeout must > 0");
			}
			_connectionTimeout = timeout;
			return this;
		}
		
		public Builder setMaxOperator(int max){
			if(max <= 0){
				throw new IllegalArgumentException("Max operator must >= 0");
			}
			_maxOperator = (max == 0 ? Integer.MAX_VALUE : max);
			return this;
		}
		
		public Builder setCharsetName(String charsetName){
			_charset = Charset.forName(charsetName);
			return this;
		}
		
		public Builder setCharset(Charset charset){
			_charset = charset;
			return this;
		}
		
		public Configure build(){
			return new Configure(_host,_port,
					_heartPoolSize,_heartbeatInitDelay,
					_heartbeatDelay,_heartbeatTimeout,
					_connectionTimeout,_maxOperator,_charset);
		}
	}
	
	private final String _host;
	private final int _port;
	private final int _heartPoolSize;
	private final int _heartbeatInitDelay ;
	private final int _heartbeatDelay ;
	private final int _heartbeatTimeout ;
	private final int _connectionTimeout;
	private final int _maxOperator;
	private final Charset _charset;
	private final InetSocketAddress _address;
	
	public Configure(String host,int port,int heartPoolSize,
			int heartbeatInitDelay, int heartbeatDelay, int heartbeatTimeout,
			int connectionTimeout,int maxOperator,Charset charset){
		
		this._host = host;
		this._port = port;
		this._heartPoolSize = heartPoolSize;
		this._heartbeatInitDelay = heartbeatInitDelay;
		this._heartbeatDelay = heartbeatDelay;
		this._heartbeatTimeout = heartbeatTimeout;
		this._connectionTimeout = connectionTimeout;
		this._maxOperator = maxOperator;
		this._charset = charset;
		_address = new InetSocketAddress(host,port);
	}
	
	public String getHost() {
		return _host;
	}
	
	public int getPort() {
		return _port;
	}
	
	public int getHeartPoolSize(){
		return _heartPoolSize;
	}
	
	public int getHeartbeatInitDelay(){
		return _heartbeatInitDelay;
	}
	
	public int getHeartbeatDelay(){
		return _heartbeatDelay;
	}
	
	public int getHeartbeatTimeout(){
		return _heartbeatTimeout;
	}
	
	public int getConnectionTimeout(){
		return _connectionTimeout;
	}
	
	public Charset getCharset(){
		return _charset;
	}
	
	public InetSocketAddress getAddress(){
		return _address;
	}
	
	public int getMaxOperator() {
		return _maxOperator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_host == null) ? 0 : _host.hashCode());
		result = prime * result + _port;
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
		Configure other = (Configure) obj;
		if (_host == null) {
			if (other._host != null)
				return false;
		} else if (!_host.equals(other._host))
			return false;
		if (_port != other._port)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Configure [_host=");
		builder2.append(_host);
		builder2.append(", _port=");
		builder2.append(_port);
		builder2.append(", _heartPoolSize=");
		builder2.append(_heartPoolSize);
		builder2.append(", _heartbeatInitDelay=");
		builder2.append(_heartbeatInitDelay);
		builder2.append(", _heartbeatDelay=");
		builder2.append(_heartbeatDelay);
		builder2.append(", _heartbeatTimeout=");
		builder2.append(_heartbeatTimeout);
		builder2.append(", _connectionTimeout=");
		builder2.append(_connectionTimeout);
		builder2.append(", _maxOperator=");
		builder2.append(_maxOperator);
		builder2.append(", _charset=");
		builder2.append(_charset);
		builder2.append("]");
		return builder2.toString();
	}
}
