package com.tcc.cti.core.message.response;

import com.tcc.cti.core.message.MessageType;

public class ResumeResponse extends ResponseMessage {

	private final String _result;
	
	public ResumeResponse(String companyId, String opId,
			String seq, String result) {
		super(companyId, opId, MessageType.Resume.response(), seq);
		_result = result;
	}
	
	public String getResult(){
		return _result;
	}

	@Override
	public String toString() {
		return "ResumeResponse [_result=" + _result + ", _companyId="
				+ _companyId + ", _opId=" + _opId + ", _messageType="
				+ _messageType + ", _seq=" + _seq + "]";
	}
	
	

}
