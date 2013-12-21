package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Resume;


public class ResumeReceiveHandler extends AbstractReceiveHandler{
	
	@Override
	protected boolean isReceive(String msgType) {
		return Resume.isRequest(msgType);
	}

	@Override
	protected String getMessageType() {
		return Resume.request();
	}
}
