package com.tcc.cti.core.message.response;

import static com.tcc.cti.core.message.MessageType.Silence;

public class SilenceResponse extends ResponseMessage {
	private final String _result;
	
	public SilenceResponse(String companyId, String opId,String seq,String result) {
		
		this(companyId, opId, Silence.response(), seq,result);
	}
	
	protected SilenceResponse(String companyId,String opId,
			String messageType,String seq,String result){
		
		super(companyId, opId, messageType, seq);
		_result = result;
	}
	
	public String getResult(){
		return _result;
	}

	@Override
	public String toString() {
		return "SilenceResponse [_result=" + _result + ", _companyId="
				+ _companyId + ", _opId=" + _opId + ", _messageType="
				+ _messageType + ", _seq=" + _seq + "]";
	}

}
