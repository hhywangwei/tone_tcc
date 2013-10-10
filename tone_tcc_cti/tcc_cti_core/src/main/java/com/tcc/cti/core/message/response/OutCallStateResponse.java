package com.tcc.cti.core.message.response;

import static com.tcc.cti.core.message.response.ResponseCode.InstanceCode;
import static com.tcc.cti.core.message.MessageType.OutCallState;

public class OutCallStateResponse extends ResponseMessage{
	private static final String PREFIX_DETAIL_TEMPLATE = "out_call_%s";
	
	private final String _callLeg;
	private final String _state;
	private final String _detail;
	
	public OutCallStateResponse(String companyId, String opId,
			String seq,String callLeg,String state) {
		
		super(companyId, opId, OutCallState.response(), seq);
		_callLeg = callLeg;
		_state = state;
		_detail = InstanceCode.getDetail(
				String.format(PREFIX_DETAIL_TEMPLATE, state));
	}
	
	public String getCallLeg(){
		return _callLeg;
	}
	
	public String getState(){
		return _state;
	}
	
	public String getDetail(){
		return _detail;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OutCallStateResponse [_callLeg=");
		builder.append(_callLeg);
		builder.append(", _state=");
		builder.append(_state);
		builder.append(", _detail=");
		builder.append(_detail);
		builder.append(", _companyId=");
		builder.append(_companyId);
		builder.append(", _opId=");
		builder.append(_opId);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append(", _seq=");
		builder.append(_seq);
		builder.append("]");
		return builder.toString();
	}
}
