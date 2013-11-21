package com.tcc.cti.core.client.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.client.OperatorChannel;
import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.monitor.event.HeartbeatEvent;
import com.tcc.cti.core.client.monitor.event.NoneHeartbeatEvent;
import com.tcc.cti.core.message.request.HeartbeatRequest;

/**
 * 发送心跳任务
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class HeartbeatSendTask implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(HeartbeatSendTask.class);
	private static final HeartbeatRequest HB_REQUEST = new HeartbeatRequest();
	
	private final OperatorChannel _channel;
	private HeartbeatEvent _event = new NoneHeartbeatEvent();
	
	public HeartbeatSendTask(OperatorChannel channel){
		_channel = channel;
	}
	
	@Override
	public void run() {
		if(_channel.isStart()){
			sendHeartbeat(_channel);
		}
	}
	
	private void sendHeartbeat(OperatorChannel channel){
		try{
			logger.debug("{}-{} send hb......",new Date(),channel.getOperatorKey());
			_channel.send(HB_REQUEST);
		}catch(ClientException e){
			OperatorKey ok = channel.getOperatorKey();
			logger.error("{} Heartbeat send is error:{}",ok.toString(),e.getMessage());
			_event.fail(e);
		}
	}
	
	public void setEvent(HeartbeatEvent event){
		_event = event;
	}
}