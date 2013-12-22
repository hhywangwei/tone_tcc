package com.tcc.cti.driver.session.process;

import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;

public interface Requestsable {

	void put(String messageType,String seq,Requestable<? extends Response> request);
	
	Requestable<? extends Response> get(String messageType,String seq);
	
	void remove(String messageType,String seq);
}
