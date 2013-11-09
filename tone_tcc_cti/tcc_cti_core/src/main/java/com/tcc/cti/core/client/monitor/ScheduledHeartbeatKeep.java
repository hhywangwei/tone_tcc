package com.tcc.cti.core.client.monitor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.OperatorChannel;
import com.tcc.cti.core.client.monitor.event.HeartbeatEvent;
import com.tcc.cti.core.client.monitor.event.NoneHeartbeatEvent;
import com.tcc.cti.core.client.task.HeartbeatSendTask;

/**
 * 实现心跳程序
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class ScheduledHeartbeatKeep implements HeartbeatKeepable, HeartbeatListener{
	private static final Logger logger = LoggerFactory.getLogger(ScheduledHeartbeatKeep.class);
	private static final int DEFAULT_INIT_DELAY = 0;
	private static final int DEFAULT_DELAY = 20;
	
	private Object _monitor = new Object();
	private OperatorChannel _channel;
	private int _initDelay;
	private int _delay;
	private ScheduledExecutorService _executorService;
	private volatile ScheduledFuture<?> _future ;
	private volatile boolean _independent = false;
	
	private HeartbeatEvent _event = new NoneHeartbeatEvent();
	
	public ScheduledHeartbeatKeep(OperatorChannel channel){
		init(channel,null,DEFAULT_INIT_DELAY,DEFAULT_DELAY);
	}
	
	public ScheduledHeartbeatKeep(OperatorChannel channel,
			ScheduledExecutorService executorService,int initDelay,int delay){
		
		init(channel,executorService,initDelay,delay);
	}
	
	private void init(OperatorChannel channel,
			ScheduledExecutorService executorService,int initDelay,int delay){
	 
		_channel = channel;
		_executorService = executorService;
		_initDelay = initDelay;
		_delay = delay;
	}
	
	@Override
	public void start() {
		synchronized (_monitor) {
			if(_executorService == null){
				logger.debug("Start new single thread scheduled...");
				_executorService = Executors.newSingleThreadScheduledExecutor();
				_independent = true;
			}
			HeartbeatSendTask runner = new HeartbeatSendTask(_channel);
			runner.setEvent(_event);
			_future = _executorService.scheduleWithFixedDelay(
					runner, _initDelay, _delay, TimeUnit.SECONDS);	
		}
	}
	
	@Override
	public void shutdown() {
		synchronized (_monitor) {
			if(_future == null){
				return ;
			}
			
			_future.cancel(true);
			
			if(_independent){
				_executorService.shutdownNow();		
			}
		}
	}
	
	@Override
	public void listener(HeartbeatEvent event){
		_event = event;
	}

}
