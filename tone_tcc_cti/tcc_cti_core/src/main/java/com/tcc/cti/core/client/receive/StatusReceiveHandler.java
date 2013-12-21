package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Status;

public class StatusReceiveHandler extends AbstractReceiveHandler {
	
	@Override
	protected boolean isReceive(String msgType) {
		return Status.isResponse(msgType);
	}

	@Override
	protected String getMessageType() {
		return Status.request();
	}
}
