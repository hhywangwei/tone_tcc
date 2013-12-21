package com.tcc.cti.core.client.session.process;

import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

public interface Requestsable {

	void put(String messageType,String seq,Requestable<? extends Response> request);
	
	Requestable<? extends Response> get(String messageType,String seq);
	
	void remove(String messageType,String seq);
}
