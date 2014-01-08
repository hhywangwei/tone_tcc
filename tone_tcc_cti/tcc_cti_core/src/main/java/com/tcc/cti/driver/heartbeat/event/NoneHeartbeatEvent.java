package com.tcc.cti.driver.heartbeat.event;

public class NoneHeartbeatEvent implements HeartbeatEvent {

    @Override
    public void success() {
		// none instance

    }

    @Override
    public void fail(Throwable e) {
        // none instance
    }
}
