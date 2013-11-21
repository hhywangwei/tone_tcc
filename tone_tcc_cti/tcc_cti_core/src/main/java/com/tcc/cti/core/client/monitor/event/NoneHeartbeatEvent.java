package com.tcc.cti.core.client.monitor.event;

public class NoneHeartbeatEvent implements HeartbeatEvent{

	@Override
	public void success() {
		// none instance
		
	}

	@Override
	public void fail(Throwable e) {
		// none instance
	}
}