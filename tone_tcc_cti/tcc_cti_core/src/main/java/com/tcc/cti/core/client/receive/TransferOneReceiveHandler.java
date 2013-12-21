package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.TransferOne;

public class TransferOneReceiveHandler extends AbstractReceiveHandler {
	
	@Override
	protected boolean isReceive(String msgType) {
		return TransferOne.isResponse(msgType);
	}

	@Override
	protected String getMessageType() {
		return TransferOne.request();
	}
}
