package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Logout;

import com.tcc.cti.core.message.response.Response;

public class LogoutRequest extends BaseRequest<Response> {

	public LogoutRequest() {
		super(Logout.request());
	}
	
	public LogoutRequest(int timeout){
		super(Logout.request(),timeout);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LogoutRequest [_messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
}
