package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.TransferGroup;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.RequestMessage;
import com.tcc.cti.core.message.request.TransferGroupRequest;

public class TransferGroupSendHanlder extends AbstractSendHandler {
	private static final String CALLLEG_FORMAT = "<CallLeg>%s</CallLeg>";
	private static final String GROUPID_FORMAT = "<GroupID>%s</GroupID>";

	@Override
	protected boolean isSend(RequestMessage message) {
		return TransferGroup.isRequest(message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message, OperatorKey key,
			GeneratorSeq generator) {
		
		TransferGroupRequest request = (TransferGroupRequest)message;
		StringBuilder sb = new StringBuilder(512);
		sb.append(String.format(MSG_FORMAT, request.getMessageType()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(COMPANY_ID_FORMAT,key.getCompanyId()));
		sb.append(String.format(OPID_FORMAT, key.getOpId()));
		sb.append(String.format(CALLLEG_FORMAT, request.getCallLeg()));
		sb.append(String.format(GROUPID_FORMAT,request.getGroupId()));
		
		return sb.toString();
	}

}
