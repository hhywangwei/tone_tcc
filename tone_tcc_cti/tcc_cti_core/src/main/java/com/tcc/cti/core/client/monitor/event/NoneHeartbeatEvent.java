package com.tcc.cti.core.client.monitor.event;

import java.nio.ByteBuffer;

public class NoneHeartbeatEvent implements HeartbeatEvent{

	@Override
	public void success(ByteBuffer buffer) {
		// none instance
		
	}

	@Override
	public void fail(ByteBuffer buffer, Throwable e) {
		// none instance
	}
}