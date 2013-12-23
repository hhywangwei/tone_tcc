package com.tcc.cti.driver.message.event;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;

public class NoneRequestEvent implements RequestEvent{

	@Override
	public void beforeSend(Operator opertor, String seq,
			Requestable<? extends Response> request) {
		
		// None instance
	}

	@Override
	public void finishReceive(Operator operator, String seq) {
		
		// None instance
	}

	 
}
