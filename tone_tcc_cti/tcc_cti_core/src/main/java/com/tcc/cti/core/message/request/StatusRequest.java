package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Status;

public class StatusRequest extends RequestMessage{
	
	private String _workId;
	private String _status;

	public StatusRequest() {
		super(Status.request());
	}
	
	public void setWorkId(String workId){
		_workId = workId;
	}
	
	public String getWorkId(){
		return _workId;
	}
	
	public void setStatus(String status){
		_status = status;
	}
	
	public String getStatus(){
		return _status;
	}

	@Override
	public String toString() {
		return "StatusRequest [_workId=" + _workId + ", _status=" + _status
				+ ", _messageType=" + _messageType + "]";
	}
}
