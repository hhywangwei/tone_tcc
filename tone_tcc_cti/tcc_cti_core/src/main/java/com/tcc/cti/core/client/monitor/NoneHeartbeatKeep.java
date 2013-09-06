package com.tcc.cti.core.client.monitor;

import com.tcc.cti.core.client.OperatorChannel;

public class NoneHeartbeatKeep implements HeartbeatKeepable{

	@Override
	public void start() {
		//none instance
	}

	@Override
	public boolean register(OperatorChannel channel) {
		//none instance	
		return false;
	}

	@Override
	public void unRegister(OperatorChannel channel) {
		//none instance		
	}

	@Override
	public void shutdown() {
		//none instance		
	}

	@Override
	public boolean contains(OperatorChannel channel) {
		//none instance
		return false;
	}
}
