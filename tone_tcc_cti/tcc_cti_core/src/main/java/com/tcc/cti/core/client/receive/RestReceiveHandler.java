package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Rest;

public class RestReceiveHandler extends AbstractReceiveHandler{
	
	@Override
	protected boolean isReceive(String msgType) {
		return Rest.isResponse(msgType);
	}

	@Override
	protected String getMessageType() {
		return Rest.request();
	}

 
}
