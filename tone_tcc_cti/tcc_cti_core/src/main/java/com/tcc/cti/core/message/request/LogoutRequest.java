package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Logout;

public class LogoutRequest extends RequestMessage {

	public LogoutRequest() {
		super(Logout.request());
	}

}
