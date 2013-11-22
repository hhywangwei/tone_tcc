package com.tcc.cti.core.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
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
import com.tcc.cti.core.client.session.Session;
import com.tcc.cti.core.client.session.Sessionable.Status;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.client.task.StocketReceiveTask;
import com.tcc.cti.core.message.pool.CtiMessagePool;
import com.tcc.cti.core.message.pool.OperatorCtiMessagePool;
import com.tcc.cti.core.message.request.RequestMessage;
import com.tcc.cti.core.model.ServerConfigure;

/**
 * 通过TCP协议实现客户端与CTI服务器消息通信
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class TcpCtiClient implements CtiClientable{
	private static final Logger logger = LoggerFactory.getLogger(TcpCtiClient.class);
	private static final String DEFAULT_CHARSET_NAME = "ISO-8859-1";
	private static final int DEFAULT_TIMEOUT = 30 * 1000;
	private static final int HEART_POOL_SIZE = 5;

	private final CtiMessagePool _messagePool;
	private final InetSocketAddress _address;
	private final ConcurrentHashMap<OperatorKey, Session> _channelPool = 
			new ConcurrentHashMap<OperatorKey, Session>();
	private final Object _monitor = new Object();
	
	private List<SendHandler> _sendHandlers;
	private List<ReceiveHandler> _receiveHandlers;
	private StocketReceiveTask _receiveTask;
	private Thread _receiveThread;
	private Selector _selector;
	private ScheduledExecutorService _heartExcecutorService;
	private int _timeout = DEFAULT_TIMEOUT;
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
		OperatorKey key = new OperatorKey(companyId, opId);
		Session session = null;
		boolean success = false;
		
		synchronized (_monitor) {
			if(!_start){
				throw new ClientException("TcpCtiClient must start");
			}
			
			if(isRegister(key)){
				return true;
			}
			
			Connectionable conn = 
					new NioConnection(_selector,_address,_timeout);
			session = new Session.
					Builder(key,conn,_messagePool).
					setScheduledExecutorService(_heartExcecutorService).
					setReceiveHandlers(_receiveHandlers).
					setSendHandlers(_sendHandlers).
					setCharset(_charset).
					build();
			_channelPool.put(key, session);
		}
		
		try{
			_receiveTask.suspend();
			session.start();
			success = true;
		}catch(IOException e){
			logger.error("Register {} is error:{}",
					key.toString(),e.toString());
			throw new ClientException(e);
		}finally{
			_receiveTask.restart();
		}
		
		return success;
	}
	
	private boolean isRegister(OperatorKey key){
		Sessionable oc = _channelPool.get(key);
		return oc != null;
	}
	
	@Override
	public void unRegister(String companyId,String opId)throws ClientException{
		synchronized (_monitor) {
			if(!_start){
				return ;
			}
			OperatorKey key = new OperatorKey(companyId, opId);
			Sessionable  oc = _channelPool.remove(key);
			if(oc == null){
				return ;
			}
			try{
				oc.close();			
			}catch(IOException e){
				logger.error("Unregister {} is error:{}",
						key.toString(),e.toString());
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
		Sessionable channel = _channelPool.get(key);
		try{
			channel.send(message);	
		}catch(IOException e){
			logger.error("Send message is error {}",e.toString());
			throw new ClientException(e);
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
	public void setTimeout(int timeout){
		_timeout = timeout * 1000 ;
	}
	
	@Override
	public void setCharset(String charset){
		_charset = charset;
	}
	
	@Override
	public CtiMessagePool getMessagePool(){
		return _messagePool;
	}

	@Override
	public Status getStatus(String companyId, String opId) {
		// TODO Auto-generated method stub
		return null;
	}
}
