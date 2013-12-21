package com.tcc.cti.core.message.event;

import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

public interface RequestEvent {
	
	void startRequest(String messageType,String seq,Requestable<? extends Response> request);
	
	void finishRequest(String messageType,String seq);
}
