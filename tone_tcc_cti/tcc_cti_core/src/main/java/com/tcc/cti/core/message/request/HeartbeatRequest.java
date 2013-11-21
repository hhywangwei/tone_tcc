package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Heartbeat;

/**
 * 心跳
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class HeartbeatRequest extends RequestMessage{

	public HeartbeatRequest() {
		super(Heartbeat.request());
	}
}
