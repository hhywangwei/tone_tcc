package com.tcc.cti.core.message.send;

import com.tcc.cti.core.message.MessageType;

public class SelfInfoSendMessage extends SendMessage{

	public SelfInfoSendMessage() {
		super(MessageType.SelfInfo.getType());
	}
}
