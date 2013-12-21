package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.TransferGroup;

import com.tcc.cti.core.message.response.Response;

public class TransferGroupRequest extends PhoneStatusRequest<Response> {
	
	private String _groupId;
	
	public TransferGroupRequest() {
		super(TransferGroup.request());
	}
	
	public void setGroupId(String groupId){
		_groupId = groupId;
	}
	
	public String getGroupId(){
		return _groupId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransferGroupRequest [_groupId=");
		builder.append(_groupId);
		builder.append(", _callLeg=");
		builder.append(_callLeg);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
}
