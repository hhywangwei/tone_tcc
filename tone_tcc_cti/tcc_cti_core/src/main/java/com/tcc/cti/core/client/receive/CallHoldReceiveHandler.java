package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.CallHold;

public class CallHoldReceiveHandler extends AbstractReceiveHandler {

	@Override
	protected boolean isReceive(String msgType) {
		return CallHold.isResponse(msgType);
	}

	@Override
	protected String getMessageType() {
		return CallHold.request();
	}

	
}
