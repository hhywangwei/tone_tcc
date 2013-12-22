package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Heartbeat;

import java.util.List;

import com.tcc.cti.core.message.event.RequestEvent;
import com.tcc.cti.core.message.response.Response;

/**
 * 心跳
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class HeartbeatRequest implements Requestable<Response>{

	@Override
	public String getMessageType() {
		return Heartbeat.request();
	}

	@Override
	public void receive(Response response) {
		//not instance
	}

	@Override
	public void notifySend(String seq) {
		// None instance
		
	}

	@Override
	public void notifySendError(Throwable e) {
		// None instance
		
	}

	@Override
	public void regsiterEvent(RequestEvent event) {
		// None instance
		
	}
	
	@Override
	public List<Response> response() throws InterruptedException {
		//not instance
		return null;
	}

	@Override
	public List<Response> response(int timeout) throws InterruptedException {
		// None instance
		return null;
	}
}
