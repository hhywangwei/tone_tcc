package com.tcc.cti.core.message.request;

import java.util.ArrayList;

import com.tcc.cti.core.message.response.Response;

public class MultiBaseRequest<T extends Response> extends BaseRequest<T> {
	private static final String COMPLETE_PREFIX = "-";
	public MultiBaseRequest(String messageType) {
		super(messageType,new ArrayList<T>());
	}
	
	@Override
	public void receive(T response) {
		synchronized (_monitor) {
			_complete = isComplete(response);
			if(_complete){
				_monitor.notifyAll();
			}else{
				_responses.add(response);
			}
		}
	}
	
	private boolean isComplete(T response){
		String seq = response.getSeq();
		if(seq == null){
			return false;
		}
		return seq.startsWith(COMPLETE_PREFIX);
	}
}
