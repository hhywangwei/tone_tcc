package com.tcc.cti.driver.message.event;

import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;

public interface RequestEvent {
	
	void startRequest(String messageType,String seq,Requestable<? extends Response> request);
	
	void finishRequest(String messageType,String seq);
}