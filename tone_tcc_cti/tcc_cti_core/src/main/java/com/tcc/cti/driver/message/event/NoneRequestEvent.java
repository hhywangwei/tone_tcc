package com.tcc.cti.driver.message.event;

import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.message.token.Tokenable;

public class NoneRequestEvent implements RequestEvent{

	@Override
	public void beforeSend(Tokenable token,Requestable<? extends Response> request) {
		
		// None instance
	}

	@Override
	public void finishReceive(Tokenable token) {
		
		// None instance
	}

	 
}
