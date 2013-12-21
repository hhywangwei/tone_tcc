package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.TransferGroup;

public class TransferGroupReceiveHanlder extends AbstractReceiveHandler{
	
	@Override
	protected boolean isReceive(String msgType) {
		return TransferGroup.isResponse(msgType);
	}

	@Override
	protected String getMessageType() {
		return TransferGroup.request();
	}
}
