package com.tcc.cti.core.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.connection.Connectionable;
import com.tcc.cti.core.client.connection.NioConnection;
import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.client.task.StocketReceiveTask;
import com.tcc.cti.core.message.pool.CtiMessagePool;
import com.tcc.cti.core.message.pool.OperatorCtiMessagePool;
import com.tcc.cti.core.message.request.RequestMessage;
import com.tcc.cti.core.model.ServerConfigure;

/**
 * 通过tcp协议实现客户端与cti服务器消息通信
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class TcpCtiClient implements CtiClientable{
	static final Logger logger = LoggerFactory.getLogger(TcpCtiClient.class);
	private static final String DEFAULT_CHARSET_NAME = "ISO-8859-1";
	private static final int DEFAULT_TIMEOUT = 30 * 1000;
	private static final int HEART_POOL_SIZE = 5;

	private final CtiMessagePool _messagePool;
	private final InetSocketAddress _address;
	private final ConcurrentHashMap<OperatorKey, OperatorChannel> _channelPool = 
			new ConcurrentHashMap<OperatorKey, OperatorChannel>();
	private final Object _monitor = new Object();
	
	private List<SendHandler> _sendHandlers;
	private List<ReceiveHandler> _receiveHandlers;
	private StocketReceiveTask _receiveTask;
	private Thread _receiveThread;
	private Selector _selector;
	private ScheduledExecutorService _heartExcecutorService;
	private int _timeOut = DEFAULT_TIMEOUT;
	private String _charset = DEFAULT_CHARSET_NAME;
	private volatile boolean _start = false;
	
	public TcpCtiClient(ServerConfigure configure){
		this(configure,new OperatorCtiMessagePool());
	}
		
	public TcpCtiClient(ServerConfigure configure,CtiMessagePool messagePool){
		_messagePool = messagePool;
		_address = new InetSocketAddress(
				configure.getHost(), configure.getPort());
	}

	@Override
	public void start()throws ClientException {
		try {
			synchronized (_monitor) {
				_selector = Selector.open();
				_receiveTask= new StocketReceiveTask(_selector);
				_receiveThread = new Thread(_receiveTask);
				_receiveThread.start();
				_heartExcecutorService = Executors.newScheduledThreadPool(HEART_POOL_SIZE);
				_start = true;
			}
		} catch (IOException e) {
			logger.error("Tcp client start is error {}",e.toString());
			throw new ClientException(e);
		}	
	}
	
	@Override
	public boolean register(String companyId,String opId)throws ClientException{
		
		OperatorKey key = null;
		OperatorChannel oc = null;
		
		synchronized (_monitor) {
			if(!_start){
				throw new RuntimeException("TcpCtiClient must start");
			}
			
			key =new OperatorKey(companyId, opId);
			if(isRegister(key)){
				return true;
			}
			
			try{
				SocketChannel channel = SocketChannel.open();
				oc = new OperatorChannel.
						Builder(key,channel,_messagePool).
						setScheduledExecutorService(_heartExcecutorService).
						setReceiveHandlers(_receiveHandlers).
						setSendHandlers(_sendHandlers).
						setCharset(_charset).
						build();
				oc.connecting();
				_channelPool.put(key, oc);
			}catch(IOException e){
				logger.error("Open socketchannel is error {}",e.toString());
				return false;
			}
		}
		return connectionServer(oc);	
	}
	
	private boolean isRegister(OperatorKey key){
		OperatorChannel oc = _channelPool.get(key);
		
		return (oc != null) && (oc.isConnecting() || oc.isStart());
	}
	
	private boolean connectionServer(OperatorChannel oc)throws ClientException{
		boolean success = false;
		
		try {
			_receiveTask.suspend();
			Connectionable connection = new NioConnection(_selector,_address);
			if(connection.connect(oc)){
				oc.start();
				success = true;
			}else{
				String host = _address.getHostName();
				int port = _address.getPort();
				logger.error("Connection {}:{} is timeout",host,port);
				throw new ClientException("Connection "+ host + ":" + port +" is timeout");
			}
		}catch(IOException e){
			logger.error("Tcp client start is error {}",e.toString());
			throw new ClientException(e);
		}finally{
			oc.connected();
			_receiveTask.restart();
		}
		return success;
	}
	
	
	@Override
	public void unRegister(String companyId,String opId)throws ClientException{
		synchronized (_monitor) {
			if(!_start){
				return ;
			}
			OperatorKey key = new OperatorKey(companyId, opId);
			OperatorChannel  oc = _channelPool.remove(key);
			if(oc == null){
				return ;
			}
			try{
				if( oc.isStart() || oc.isConnecting()){
					oc.close();			
				}		
			}catch(IOException e){
				throw new ClientException(e);
			}
		}
	}

	@Override
	public void close()throws ClientException {
		synchronized (_monitor) {
			if(!_start){
				return ;
			}
			for(OperatorKey key : _channelPool.keySet()){
				unRegister(key.getCompanyId(),key.getOpId());
			}
			_heartExcecutorService.shutdownNow();
			_receiveThread.interrupt();
		}
	}

	@Override
	public void send(RequestMessage message)throws ClientException {
		if(!_start){
			throw new RuntimeException("TcpCtiClient must start");
		}
		OperatorKey key = new OperatorKey(
				message.getCompayId(), message.getOpId());
		OperatorChannel channel = _channelPool.get(key);
		
		if(channel.isStart()){
			channel.send(message);
		}
	}

	@Override
	public void setReceiveHandlers(List<ReceiveHandler> handlers) {
		this._receiveHandlers = handlers;
	}

	@Override
	public void setSendHandlers(List<SendHandler> handlers) {
		this._sendHandlers = handlers;
	}
	
	@Override
	public void setTimeOut(int timeOut){
		_timeOut = timeOut * 1000 ;
	}
	
	@Override
	public void setCharset(String charset){
		_charset = charset;
	}
	
	@Override
	public CtiMessagePool getMessagePool(){
		return _messagePool;
	}
}
