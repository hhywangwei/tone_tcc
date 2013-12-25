package com.tcc.cti.driver.session.process;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;

public class Requests implements Requestsable {
	private static final Logger logger = LoggerFactory.getLogger(Requests.class);
	
	private static final int DEFAULT_SIZE = 100;
	private static final String KEY_TEMPLATE = "%s-%s-%s-%s";
	
	private final Map<String,Requestable<? extends Response>> requests = 
			new ConcurrentHashMap<String,Requestable<? extends Response>>(DEFAULT_SIZE); 

	private String requestKey(Operator operator,String seq,String messageType){
		return String.format(KEY_TEMPLATE, 
				operator.getCompanyId(),operator.getOpId(),seq,messageType);
	}
	

	@Override
	public void beforeSend(Operator operator, String seq,
			Requestable<? extends Response> request) {
		
		String key = requestKey(operator,seq,request.getMessageType());
		Requestable<? extends Response> o = requests.put(key, request);
		if(o != null){
			logger.error("Request key {} is exist,Request is {}.",key,request.toString());
		}
	}

	@Override
	public void finishReceive(Operator operator, String seq,String messageType) {
		String key = requestKey(operator,seq,messageType);
		Requestable<? extends Response> o = requests.remove(key);
		if(o == null){
			logger.error("Request key {} not exist");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void recevie(Operator operator, String seq,String messageType, Response response) {
		String key = requestKey(operator,seq,messageType);
		Requestable<Response> o =(Requestable<Response>) requests.get(key);
		if(o == null){
			logger.error("Request key {} not exist");
			return ;
		}
		o.receive(response);
	}
}
