package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.TransferOne;

import com.tcc.cti.core.message.response.Response;

public class TransferOneRequest extends PhoneStatusRequest<Response> {
	private String _workId;
	private String _number;

	public TransferOneRequest() {
		super(TransferOne.request());
	}
	
	public void setWorkId(String workId){
		_workId = workId;
	}
	
	public String getWorkId(){
		return _workId;
	}
	
	public void setNumber(String number){
		_number = number;
	}
	
	public String getNumber(){
		return _number;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransferOneRequest [_workId=");
		builder.append(_workId);
		builder.append(", _number=");
		builder.append(_number);
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