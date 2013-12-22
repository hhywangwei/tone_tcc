package com.tcc.cti.driver.heartbeat;

import com.tcc.cti.driver.heartbeat.event.HeartbeatEvent;

public interface HeartbeatListener {

	void listener(HeartbeatEvent event);

}
