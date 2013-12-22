package com.tcc.cti.core.message.request;

import com.tcc.cti.core.message.response.Response;

public class PhoneStatusRequest<T extends Response> extends BaseRequest<T> {
	protected String _callLeg;

	public PhoneStatusRequest(String messageType) {
		super(messageType);
	}
	
	public void setCallLeg(String callLeg){
		_callLeg = callLeg;
	}
	
	public String getCallLeg(){
		return _callLeg;
	}
}
