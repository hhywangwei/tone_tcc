package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.CallHelp;

public class CallHelpReceiveHandler extends AbstractReceiveHandler {
	
	@Override
	protected boolean isReceive(String msgType) {
		return CallHelp.isResponse(msgType);
	}

	@Override
	protected String getMessageType() {
		return CallHelp.request();
	}
}
