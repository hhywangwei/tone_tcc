package com.tcc.cti.driver.session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.driver.Operator;

public class SessionManager {
	private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
	private static final int CLEAN_SESSION_PERCENT = 70;
	
	private static SessionManager _manager = initManager();
	private final Map<Operator, Sessionable> _sessions ;
	private final Object _monitor ;
	private final AtomicInteger _count ;
	
	private int _max = 512;
	private int _cleanFireLine =  mathCleanFireLine(_max);
	 	
	public SessionManager(){
		_sessions =new ConcurrentHashMap<>(20);
		_monitor = new Object();
		_count = new AtomicInteger(0);
	}
	
	public Sessionable getSession(Operator key){
		Sessionable session =   _sessions.get(key);
		if(session == null){
			return null;
		}
		if(notActiveSession(session)){
			removeSession(key);
			return null;
		}
		return session;
	}
	
	private boolean notActiveSession(Sessionable session){
		return session.isTimeout() || session.isClose();
	}
	
	public void addSession(Operator key,Sessionable session)throws SessionRegisterException{
		if(isOverMax()){
			logger.error("Over max session ...");
			throw new SessionRegisterException(key,"Alread over max session");
		}
		synchronized (_monitor) {
			Sessionable s = _sessions.put(key, session);
			if(s == null){
				_count.incrementAndGet();
				if(isCleanSession()){
					logger.debug("Session size is {},Start clean ...",_count.get());
					cleanSession();
				}
			}	
		}
	}
	
	private boolean isOverMax(){
		return _max < _count.get();
	}
	
	private boolean isCleanSession(){
		return _cleanFireLine < _count.get();
	}
	
	private void cleanSession(){
		Iterator<Operator> iterator = _sessions.keySet().iterator();
		for(;iterator.hasNext();){
			Operator k = iterator.next();
			Sessionable s = _sessions.get(k);
			boolean remove = notActiveSession(s);
			if(s.isTimeout()){
				closeSession(s);
			}
			if(remove){
				iterator.remove();
				_count.decrementAndGet();
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
			Operator key = session.getOperator();
			logger.error("Unregister {} is error:{}",key.toString(),e.toString());
		}
	}
	
	public void removeSession(Operator key){
		synchronized (_monitor) {
			Sessionable s = _sessions.get(key);
			if(s == null){
				return ;
			}
			boolean remove = s.isTimeout() || s.isClose();
			if(s.isTimeout()){
				closeSession(s);
			}
			if(remove){
				_sessions.remove(key);
				_count.decrementAndGet();
			}	
		}
	}
	
	public void setMaxSession(int max){
		_max = max;
		_cleanFireLine = mathCleanFireLine(max);
	}
	
	public List<Sessionable> getSessions(){
		
		List<Sessionable> s = new ArrayList<Sessionable>();
		s.addAll(_sessions.values());
		
		return s;
	}
	
	public void close(){
		synchronized (_monitor) {
			for(Sessionable session : _sessions.values()){
				closeSession(session);
			}	
			_sessions.clear();
		}
	}
	
	private int mathCleanFireLine(int max){
		return (max * CLEAN_SESSION_PERCENT/100);
	}
	
	private static SessionManager initManager(){
		return new SessionManager();
	}
	
	public static SessionManager getManager(){
		if(_manager == null){
			_manager = initManager();
		}
		return _manager;
	}
}
