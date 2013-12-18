package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.TransferGroup;

public class TransferGroupRequest extends RequestMessage {
	
	private String _groupId;
	private String _callLeg;
	
	public TransferGroupRequest() {
		super(TransferGroup.request());
	}
	
	public void setGroupId(String groupId){
		_groupId = groupId;
	}
	
	public String getGroupId(){
		return _groupId;
	}
	
	public void setCallLeg(String callLeg){
		_callLeg = callLeg;
	}

	public String getCallLeg(){
		return _callLeg;
	}

	@Override
	public String toString() {
		return "TransferGroupRequest [_groupId=" + _groupId + ", _callLeg="
				+ _callLeg + ", _messageType=" + _messageType + "]";
	}
	
}
