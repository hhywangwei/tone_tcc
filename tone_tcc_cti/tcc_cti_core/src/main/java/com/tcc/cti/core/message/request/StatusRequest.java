package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Status;

import com.tcc.cti.core.message.response.Response;

public class StatusRequest extends BaseRequest<Response>{
	
	private String _workId;
	private String _status;

	public StatusRequest() {
		super(Status.request());
	}
	
	public StatusRequest(int timeout){
		super(Status.request(),timeout);
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
		StringBuilder builder = new StringBuilder();
		builder.append("StatusRequest [_workId=");
		builder.append(_workId);
		builder.append(", _status=");
		builder.append(_status);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
}
