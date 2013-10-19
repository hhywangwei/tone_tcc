package com.tcc.cti.core.message.pool;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.response.ResponseMessage;

/**
 * 实现以话务员为单位的消息池,每个话务员消息池有最大存活时间{@code ttl},。
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OperatorCtiMessagePool implements CtiMessagePool {
	private static final Logger logger = LoggerFactory.getLogger(OperatorCtiMessagePool.class);
	private static final int DEFAULT_TTL = 2 * 60 * 1000;
	private static final int DEFAULT_MESSAGE_TIMEOUT = 20 * 1000;
	
    private final int _ttl;
    private final int _messageTimeout;
    private final ConcurrentHashMap<OperatorKey,MessageEntry> _pool;
	
	public OperatorCtiMessagePool(){
		this(DEFAULT_TTL,DEFAULT_MESSAGE_TIMEOUT);
	}
	
	public OperatorCtiMessagePool(int ttl,int messageTimeout){
		_ttl = ttl;
		_messageTimeout = messageTimeout;
		_pool = new ConcurrentHashMap<>();
	}

	@Override
	public void put(String companyId, String opId, ResponseMessage message)throws InterruptedException {
		OperatorKey key = new OperatorKey(companyId,opId);
		MessageEntry m = _pool.putIfAbsent(key,new MessageEntry(_ttl,_messageTimeout));
		if(m == null){
			logger.debug("{} message pool create",key.toString());
			m = _pool.get(key);
		}
		m.put(message);
	}

	@Override
	public ResponseMessage poll(String companyId, String opId) throws InterruptedException{
		OperatorKey key = new OperatorKey(companyId,opId);
		MessageEntry m = _pool.get(key);
		return m == null ? null : m.poll();
	}
	
	@Override
	public void compact(){
		Iterator<Entry<OperatorKey,MessageEntry>> iterator =
				_pool.entrySet().iterator();
		
		for( ;iterator.hasNext(); ){
			Entry<OperatorKey,MessageEntry> e = iterator.next();
			if(e.getValue().expire()){
				iterator.remove();
			}
		}
	}
	
	static class MessageEntry{
		
		private final BlockingQueue<ResponseMessage> _messages;
		private final int _ttl;
		private final int _messageTimeout;
		private volatile long _lastTime;
		
		MessageEntry(int ttl,int messageTimeout){
			_messages = new LinkedBlockingQueue<ResponseMessage>();
			_ttl = ttl;
			_messageTimeout = messageTimeout;
			_lastTime = System.currentTimeMillis();
		}
		
		void put(ResponseMessage message)throws InterruptedException{
			_messages.put(message);
			_lastTime = System.currentTimeMillis();
		}
		
		ResponseMessage poll()throws InterruptedException{
			ResponseMessage m ;
			if(expire()){
				_messages.clear();
				m = null;
			}else{
				m =  _messages.poll(_messageTimeout, TimeUnit.MILLISECONDS);
			}
			_lastTime = System.currentTimeMillis();
			return m;
		}
		
		boolean expire(){
			long deff =  System.currentTimeMillis() - _lastTime;
			return (new Long(deff)).intValue() > _ttl;
		}

	}
}
