package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.TransferOne;

public class TransferOneRequest extends RequestMessage {
	private String _callLeg;
	private String _workId;
	private String _number;

	public TransferOneRequest() {
		super(TransferOne.request());
	}
	
	public void setCallLeg(String callLeg){
		_callLeg = callLeg;
	}
	
	public String getCallLeg(){
		return _callLeg;
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
		return "TransferOneRequest [_callLeg=" + _callLeg + ", _workId="
				+ _workId + ", _number=" + _number + ", _messageType="
				+ _messageType + "]";
	}

}
