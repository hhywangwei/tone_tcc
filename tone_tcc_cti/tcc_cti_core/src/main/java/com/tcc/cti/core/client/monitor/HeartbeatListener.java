package com.tcc.cti.core.client.monitor;

import com.tcc.cti.core.client.monitor.event.HeartbeatEvent;

public interface HeartbeatListener {

	void listener(HeartbeatEvent event);

}
