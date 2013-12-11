package com.tcc.cti.core.client.session;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.Configure;
import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.heartbeat.HeartbeatKeepable;
import com.tcc.cti.core.client.heartbeat.ScheduledHeartbeatKeep;
import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.client.session.process.MessageProcessable;
import com.tcc.cti.core.client.session.process.SingleMessageProcess;
import com.tcc.cti.core.client.session.task.StocketReceiveTask;
import com.tcc.cti.core.message.pool.CtiMessagePool;
import com.tcc.cti.core.message.pool.OperatorCtiMessagePool;

/**
 * 用户连接服务端Session Factory,得到Session可与CTI服务端通讯
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class SessionFactory {
	private static final Logger logger = LoggerFactory.getLogger(SessionFactory.class);
	private static final int CLEAN_SESSION_PERCENT = 70;
	private static final Object _monitor = new Object();
	private static volatile SessionFactory factory ;
	
	private final Map<OperatorKey, Sessionable> _sessionPool = new HashMap<OperatorKey, Sessionable>();
	private ScheduledExecutorService _heartExcecutorService;
	
	private final Configure _configure;
	private final CtiMessagePool _messagePool;
	private final Selector _selector;
	private final StocketReceiveTask _receiveTask;
	private final Thread _receiveThread;
	private final int _cleanSessionFireLine ;
	
	private List<SendHandler> _sendHandlers;
	private List<ReceiveHandler> _receiveHandlers;
	private MessageProcessable _messageProcess;

	private SessionFactory(Configure configure){
		_configure = configure;
		_messagePool = new OperatorCtiMessagePool();
		_selector = openSelector();
		_receiveTask= new StocketReceiveTask(_selector);
		_heartExcecutorService = Executors.newScheduledThreadPool(_configure.getHeartPoolSize());
		_cleanSessionFireLine = (configure.getMaxOperator() * CLEAN_SESSION_PERCENT) / 100;
		_receiveThread = new Thread(_receiveTask);
		_receiveThread.start();
	}
	
	private Selector openSelector(){
		try{
			return Selector.open();			
		}catch(IOException e){
			logger.debug("Open selector fail,Error is {}",e.getMessage());
			throw new IllegalAccessError("Open selector fail,Error is " + e.getMessage());
		}		
	}
	
	public Sessionable getSession(OperatorKey key)throws SessionRegisterException{
		Sessionable session = _sessionPool.get(key);
		if(session != null ){
			if(session.isActive() || session.isService()){
				return session;	
			}
		}
		return register(key);
	}
	
	private Sessionable register(OperatorKey key)throws SessionRegisterException{
		Sessionable session = null;
		
		synchronized (_monitor) {
			session = _sessionPool.get(key);
			if(session != null){
				if(session.isActive() || session.isService()){
					return session;	
				}
				if(session.isTimeout()){
					closeSession(session);
				}
				_sessionPool.remove(key);
			}
			
			if(isCleanSession()){
				logger.debug("Session size is {},Start clean ...",_sessionPool.size());
				cleanSession();
			}
			
			if(isOverMaxOperator()){
				logger.error("Override max operator ...");
				throw new SessionRegisterException(key,"Alread over max operator.");
			}
			
			if(_messageProcess == null){
				_messageProcess = new SingleMessageProcess();
				_messageProcess.start();
			}
			
			session = new Session.
					Builder(key,_selector,_messageProcess,_configure,_messagePool).
					setHeartbeatKeep(createHeartbeatKeep(session)).
					setReceiveHandlers(_receiveHandlers).
					setSendHandlers(_sendHandlers).
					build();
			_sessionPool.put(key, session);
		}
		
		try{
			_receiveTask.suspend();
			session.start();
		}catch(IOException e){
			logger.error("Register {} is error:{}",	key.toString(),e.toString());
			throw new SessionRegisterException(key,e);
		}finally{
			_receiveTask.restart();
		}
		
		return session;
	}
	
	private boolean isCleanSession(){
		return _cleanSessionFireLine < _sessionPool.size();
	}
	
	private void cleanSession(){
		Iterator<OperatorKey> iterator = _sessionPool.keySet().iterator();
		for(;iterator.hasNext();){
			OperatorKey k = iterator.next();
			Sessionable s = _sessionPool.get(k);
			if(s == null){
				iterator.remove();
				continue;
			}
			if(s.isTimeout()){
				closeSession(s);
				iterator.remove();
			}
		}
	}
	
	private void closeSession(Sessionable session){
		if(session == null){
			return ;
		}
		try{
			session.close();			
		}catch(IOException e){
			OperatorKey key = session.getOperatorKey();
			logger.error("Unregister {} is error:{}",key.toString(),e.toString());
		}
	}
	
	private boolean isOverMaxOperator(){
		return _configure.getMaxOperator() < _sessionPool.size();
	}
	
	private HeartbeatKeepable createHeartbeatKeep(Sessionable session){
		return new ScheduledHeartbeatKeep(session, _heartExcecutorService,
				_configure.getHeartbeatInitDelay(), _configure.getHeartbeatDelay());
	}
	
	public void close(){
		synchronized (_monitor) {
			for(Sessionable session : _sessionPool.values()){
				closeSession(session);
			}
			_receiveThread.interrupt();
			_sessionPool.clear();
			_messageProcess.close();
			_heartExcecutorService.shutdownNow();
		}
	}
	
	public void setReceiveHandlers(List<ReceiveHandler> handlers) {
		if(handlers == null){
			throw new IllegalArgumentException("Handlers not null");
		}
		synchronized (_monitor) {
			_receiveHandlers = handlers;			
		}
	}

	public void setSendHandlers(List<SendHandler> handlers) {
		if(handlers == null){
			throw new IllegalArgumentException("Handlers not null");
		}
		synchronized (_monitor) {
			_sendHandlers = handlers;			
		}
	}
	
	public void setMessageProcess(MessageProcessable process){
		if(process == null){
			throw new IllegalArgumentException("MessageProcess not null");
		}
		synchronized (_monitor) {
			_messageProcess = process;
			_messageProcess.start();
		}
	}
	
	public static SessionFactory instance(Configure configure){
		synchronized (_monitor) {
			if(factory == null){
				factory = new SessionFactory(configure);
			}
			return factory;
		}
	}
}
