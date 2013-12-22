package com.tcc.cti.driver.message.request;

import com.tcc.cti.driver.message.response.Response;

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
