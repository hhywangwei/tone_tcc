package com.tcc.cti.driver.heartbeat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.driver.heartbeat.event.HeartbeatEvent;
import com.tcc.cti.driver.heartbeat.event.NoneHeartbeatEvent;
import com.tcc.cti.driver.session.Sessionable;

/**
 * 实现心跳程序
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class ScheduledHeartbeatKeep implements HeartbeatKeepable, HeartbeatListener{
	private static final Logger logger = LoggerFactory.getLogger(ScheduledHeartbeatKeep.class);
	private static final int DEFAULT_INIT_DELAY = 0;
	private static final int DEFAULT_DELAY = 20;
	
	private final Object _monitor = new Object();
	private final int _initDelay;
	private final int _delay;
	private final boolean _independent;
	
	private ScheduledExecutorService _executorService;
	private volatile ScheduledFuture<?> _future ;
	
	private volatile boolean _start = false;
	private volatile boolean _shutdown = false;
	
	private HeartbeatEvent _event = new NoneHeartbeatEvent();
	
	public ScheduledHeartbeatKeep(){
		this(null,DEFAULT_INIT_DELAY,DEFAULT_DELAY);
	}
	
	public ScheduledHeartbeatKeep(ScheduledExecutorService executorService){
		
		this(executorService,DEFAULT_INIT_DELAY,DEFAULT_DELAY);
	}
	
	public ScheduledHeartbeatKeep(int initDelay,int delay){
		this(null,initDelay,delay);
	}
	
	public ScheduledHeartbeatKeep(ScheduledExecutorService executorService,int initDelay,int delay){
		_initDelay = initDelay;
		_delay = delay;
		_independent = (executorService == null);
		_executorService = executorService;
	}
	
	@Override
	public void start(Sessionable session) {
		synchronized (_monitor) {
			if(_start){
				logger.debug("{} Already heartbeat", session.getOperator().toString());
				return ;
			}
			HeartbeatSendTask task = new HeartbeatSendTask(session);
			task.setEvent(_event);
			_executorService = initExecutorService(_executorService);
			_future = _executorService.scheduleWithFixedDelay(
					task, _initDelay, _delay, TimeUnit.SECONDS);
			_start = true;
		}
	}
	
	private ScheduledExecutorService initExecutorService(ScheduledExecutorService executorService){
		return executorService != null ? 
				executorService : Executors.newSingleThreadScheduledExecutor();
	}
	
	@Override
	public void shutdown() {
		synchronized (_monitor) {
			if(_start && _future != null){
				_future.cancel(true);
			}
			if(_independent && _executorService != null){
				_executorService.shutdownNow();		
			}
			_shutdown = true;
		}
	}
	
	protected ScheduledExecutorService getExecutorService(){
		return _executorService;
	}
	
	protected boolean isIndependent(){
		return _independent;
	}
	
	@Override
	public boolean isStart(){
		return _start;
	}
	
	@Override
	public boolean isShutdown(){
		return _shutdown;
	}
	
	@Override
	public void listener(HeartbeatEvent event){
		_event = event;
	}

}
