package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Resume;

import com.tcc.cti.core.message.response.Response;

public class ResumeRequest extends BaseRequest<Response> {

	public ResumeRequest() {
		super(Resume.request());
	}
	
	public ResumeRequest(int timeout){
		super(Resume.request(),timeout);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResumeRequest [_messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
}
