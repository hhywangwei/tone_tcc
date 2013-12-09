package com.tcc.cti.core.client.heartbeat;

import com.tcc.cti.core.client.heartbeat.event.HeartbeatEvent;

public interface HeartbeatListener {

	void listener(HeartbeatEvent event);

}
