package com.tcc.cti.core.message.request;

import com.tcc.cti.core.message.response.Response;

public class PhoneStatusRequest<T extends Response> extends BaseRequest<T> {
	private static final int RECEIVE_SIZE = 1;
	protected String _callLeg;

	public PhoneStatusRequest(String messageType) {
		super(messageType,RECEIVE_SIZE);
	}
	
	protected PhoneStatusRequest(String messageType,int timeout){
		super(messageType,RECEIVE_SIZE,timeout);
	}
	
	public void setCallLeg(String callLeg){
		_callLeg = callLeg;
	}
	
	public String getCallLeg(){
		return _callLeg;
	}
}
