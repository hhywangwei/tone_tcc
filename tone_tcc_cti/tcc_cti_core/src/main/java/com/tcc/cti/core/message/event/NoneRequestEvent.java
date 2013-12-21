package com.tcc.cti.core.message.event;

import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

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
