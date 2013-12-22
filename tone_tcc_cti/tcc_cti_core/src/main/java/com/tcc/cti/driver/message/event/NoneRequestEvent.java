package com.tcc.cti.driver.message.event;

import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;

public class NoneRequestEvent implements RequestEvent{

	@Override
	public void startRequest(String messageType, String seq,
			Requestable<? extends Response> request) {
		
		// none instance
	}

	@Override
	public void finishRequest(String messageType, String seq) {
		
		// none instance
	}

}
