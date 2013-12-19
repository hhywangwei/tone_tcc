package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Logout;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.LogoutRequest;
import com.tcc.cti.core.message.request.RequestMessage;

public class LogoutSendHandler extends AbstractSendHandler {

	@Override
	protected boolean isSend(RequestMessage message) {
		return Logout.isRequest(message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message, OperatorKey key,
			GeneratorSeq generator) {
		
		LogoutRequest request = (LogoutRequest)message;
		StringBuilder sb = new StringBuilder(512);
		sb.append(String.format(MSG_FORMAT, request.getMessageType()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(COMPANY_ID_FORMAT,key.getCompanyId()));
		sb.append(String.format(OPID_FORMAT, key.getOpId()));
		
		return sb.toString();
	}

}
