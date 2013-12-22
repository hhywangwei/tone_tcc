package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Silence;

import com.tcc.cti.core.message.response.Response;

public class SilenceRequest extends PhoneStatusRequest<Response> {
	private String _flag;

	public SilenceRequest() {
		super(Silence.request());
	}
	
	public void setFlag(String flag){
		_flag = flag;
	}
	
	public String getFlag(){
		return _flag;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SilenceRequest [_callLeg=");
		builder.append(_callLeg);
		builder.append(", _flag=");
		builder.append(_flag);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
}
