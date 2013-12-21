package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Logout;

public class LogoutReceiveHandler extends AbstractReceiveHandler {
	
	@Override
	protected boolean isReceive(String msgType) {
		return Logout.isResponse(msgType);
	}

	@Override
	protected String getMessageType() {
		return Logout.request();
	}

	 

}
