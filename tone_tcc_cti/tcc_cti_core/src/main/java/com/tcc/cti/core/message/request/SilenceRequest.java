package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Silence;

public class SilenceRequest extends RequestMessage {
	private String _callLeg;
	private String _flag;

	public SilenceRequest() {
		this(Silence.request());
	}
	
	protected SilenceRequest(String messageType){
		super(messageType);
	}
	
	public void setCallLeg(String callLeg){
		_callLeg = callLeg;
	}
	
	public String getCallLeg(){
		return _callLeg;
	}
	
	public void setFlag(String flag){
		_flag = flag;
	}
	
	public String getFlag(){
		return _flag;
	}

	@Override
	public String toString() {
		return "SilenceRequest [_callLeg=" + _callLeg + ", _flag=" + _flag
				+ ", _messageType=" + _messageType + "]";
	}
}
