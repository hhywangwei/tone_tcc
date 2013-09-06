package com.tcc.cti.core.client.monitor;

import java.nio.ByteBuffer;

public interface HeartbeatListener {

	public interface HeartbeatEvent{
		
		void success(ByteBuffer buffer);
		
		void fail(ByteBuffer buffer,Throwable e);
	}
	
	void listener(HeartbeatEvent event);
}
