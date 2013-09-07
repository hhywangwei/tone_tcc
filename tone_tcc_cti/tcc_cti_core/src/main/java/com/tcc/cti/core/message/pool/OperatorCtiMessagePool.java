package com.tcc.cti.core.message.pool;

import java.util.Date;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实现以话务员为单位的消息池,每个话务员消息池有最大存活时间{@code ttl}，超过最大存活时间{@code ttl}消息池自动回收。
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OperatorCtiMessagePool implements CtiMessagePool {
	private static final Logger logger = LoggerFactory.getLogger(OperatorCtiMessagePool.class);
	private static final int DEFAULT_TTL = 2 * 60 * 1000;
	private static final int DEFAULT_AUTO_CLEAR_DELAY = 1 * 60;
    private static final int POOL_SIZE = 1;
	
    private final int _ttl;
	private final ScheduledExecutorService _executorService;
	private final ConcurrentHashMap<MessagePoolKey, Queue<Object>> _pool = 
			new ConcurrentHashMap<MessagePoolKey,Queue<Object>>();
	
	private volatile boolean _run = false;
	private volatile int _autoClearDelay = DEFAULT_AUTO_CLEAR_DELAY;
	
	public OperatorCtiMessagePool(){
		this(DEFAULT_TTL);
	}
	
	public OperatorCtiMessagePool(int ttl){
		_ttl = ttl;
		_executorService = Executors.newScheduledThreadPool(POOL_SIZE);
	}

	@Override
	public void push(String companyId, String opId, Object message) {
		MessagePoolKey key = new MessagePoolKey(companyId,opId,_ttl);
		Queue<Object> q = _pool.putIfAbsent(key, new ConcurrentLinkedQueue<Object>());
		q.offer(message);
	}

	@Override
	public Object task(String companyId, String opId) {
		MessagePoolKey key = new MessagePoolKey(companyId,opId,_ttl);
		Queue<Object> q = _pool.get(key);
		return q == null ? null : q.poll();
	}
	
	@Override
	public void remove(String companyId, String opId) {
		MessagePoolKey key = new MessagePoolKey(companyId,opId,_ttl);
		_pool.remove(key);
	}
	
	@Override
	public void startAutoClearExpire() {
		if(!_run){
			_run = true;
			ClearExpireRunner runner = new ClearExpireRunner(_pool);
			_executorService.scheduleWithFixedDelay(runner, 2, _autoClearDelay, TimeUnit.SECONDS);
		}
	}

	@Override
	public void closeAutoClearExpire() {
		if(_run){
			_run = false;
			_executorService.shutdown();
		}
	}

	private static class MessagePoolKey{
		private final String _companyId;
		private final String _opId;
		private final int _ttl;
		private final Date _createTime;
		
		public MessagePoolKey(String companyId,String opId,int ttl){
			_companyId = companyId;
			_opId = opId;
			_ttl = ttl;
			_createTime = new Date();
		}
		
		public boolean expire(){
			Date now = new Date();
			long deff =  now.getTime() - _createTime.getTime();
			return (new Long(deff)).intValue() > _ttl;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((_companyId == null) ? 0 : _companyId.hashCode());
			result = prime * result + ((_opId == null) ? 0 : _opId.hashCode());
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
			MessagePoolKey other = (MessagePoolKey) obj;
			if (_companyId == null) {
				if (other._companyId != null)
					return false;
			} else if (!_companyId.equals(other._companyId))
				return false;
			if (_opId == null) {
				if (other._opId != null)
					return false;
			} else if (!_opId.equals(other._opId))
				return false;
			return true;
		}
	}
	
	private static class ClearExpireRunner implements Runnable{
		private ConcurrentHashMap<MessagePoolKey, Queue<Object>> _pool;
		
		public ClearExpireRunner(ConcurrentHashMap<MessagePoolKey, Queue<Object>> pool){
			_pool = pool;
		}
		
		@Override
		public void run() {
			Iterator<MessagePoolKey> iterator = _pool.keySet().iterator();
			while(iterator.hasNext()){
				removeExpire(iterator);
			}
		}
		
		private void removeExpire(Iterator<MessagePoolKey> iterator){
			MessagePoolKey key = iterator.next();
			if(key.expire()){
				logger.debug("Remove companyId={} and opId={}",
						key._companyId,key._opId);
				iterator.remove();
			}
		}
	}
}
