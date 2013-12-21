package com.tcc.cti.core.message.request;

import java.util.List;

import com.tcc.cti.core.message.event.RequestEvent;
import com.tcc.cti.core.message.response.Response;

public interface Requestable<T extends Response> {
	
	void regsiterEvent(RequestEvent event);
	
	String getMessageType();
	
	void send(String seq);
	
	void sendError(Throwable e);
	
	void receive(T response);
	
	List<T> respone()throws InterruptedException;
}
