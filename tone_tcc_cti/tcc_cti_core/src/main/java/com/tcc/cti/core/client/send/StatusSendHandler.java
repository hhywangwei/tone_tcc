package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Status;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.RequestMessage;
import com.tcc.cti.core.message.request.StatusRequest;

public class StatusSendHandler extends AbstractSendHandler{
	private static final String WORKID_FORMAT = "<WorkID>%s</WorkID>";
	private static final String STATUS_FORMAT = "<Status>%s</Status>";
	@Override
	protected boolean isSend(RequestMessage message) {
		return Status.isRequest(message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message, OperatorKey key,
			GeneratorSeq generator) {
		
		StatusRequest request = (StatusRequest)message;
		StringBuilder sb = new StringBuilder(512);
		sb.append(String.format(MSG_FORMAT, request.getMessageType()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(COMPANY_ID_FORMAT,key.getCompanyId()));
		sb.append(String.format(OPID_FORMAT, key.getOpId()));
		sb.append(String.format(WORKID_FORMAT, request.getWorkId()));
		sb.append(String.format(STATUS_FORMAT, request.getStatus()));
		
		return sb.toString();
	}

}
