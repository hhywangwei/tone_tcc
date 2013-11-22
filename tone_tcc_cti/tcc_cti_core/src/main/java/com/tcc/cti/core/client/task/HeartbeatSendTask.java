package com.tcc.cti.core.client.task;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.monitor.event.HeartbeatEvent;
import com.tcc.cti.core.client.monitor.event.NoneHeartbeatEvent;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.message.request.HeartbeatRequest;

/**
 * 发送心跳任务
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class HeartbeatSendTask implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(HeartbeatSendTask.class);
	private static final HeartbeatRequest HB_REQUEST = new HeartbeatRequest();
	
	private final Sessionable _session;
	private HeartbeatEvent _event = new NoneHeartbeatEvent();
	
	public HeartbeatSendTask(Sessionable session){
		_session = session;
	}
	
	@Override
	public void run() {
		if(!_session.isVaild()){
			return ;
		}
		
		OperatorKey key = _session.getOperatorKey();
		
		try{
			_session.send(HB_REQUEST);
			_event.success();
			logger.debug("{} {} send hb...",new Date(),key.toString());
		}catch(IOException e){
			_event.fail(e);
			logger.error("{} Heartbeat send is error:{}",
					key.toString(),e.getMessage());
		}
	}
	
	public void setEvent(HeartbeatEvent event){
		_event = event;
	}
}