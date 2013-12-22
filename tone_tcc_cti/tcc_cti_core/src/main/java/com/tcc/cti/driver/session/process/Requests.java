package com.tcc.cti.driver.session.process;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tcc.cti.driver.message.event.RequestEvent;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;

public class Requests implements Requestsable,RequestEvent {
	private static final int DEFAULT_SIZE = 100;
	private static final String KEY_TEMPLATE = "%s-%s";
	
	private final Map<String,Requestable<? extends Response>> requests = 
			new ConcurrentHashMap<String,Requestable<? extends Response>>(DEFAULT_SIZE); 

	@Override
	public void put(String messageType, String seq,
			Requestable<? extends Response> request) {
		
		String key = createKey(messageType,seq);
		requests.put(key, request);
	}

	private String createKey(String messageType,String seq){
		return String.format(KEY_TEMPLATE, messageType,seq);
	}
	
	@Override
	public Requestable<? extends Response> get(String messageType, String seq) {
		String key = createKey(messageType,seq);
		return requests.get(key);
	}

	@Override
	public void remove(String messageType, String seq) {
		String key = createKey(messageType,seq);
		requests.remove(key);
	}

	@Override
	public void startRequest(String messageType, String seq,
			Requestable<? extends Response> request) {
		
		put(messageType,seq,request);
	}

	@Override
	public void finishRequest(String messageType, String seq) {
		remove(messageType,seq);
	}
}
