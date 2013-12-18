package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.TransferOne;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.RequestMessage;
import com.tcc.cti.core.message.request.TransferOneRequest;

public class TransferOneSendHandler extends AbstractSendHandler {
	private static final String CALLLEG_FORMAT = "<CallLeg>%s</CallLeg>";
	private static final String WORKID_FORMAT = "<WorkID>%s</WorkID>";
	private static final String NUMBER_FORMAT = "<Number>%s</Number>";
	
	@Override
	protected boolean isSend(RequestMessage message) {
		return TransferOne.isRequest(message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message, OperatorKey key,
			GeneratorSeq generator) {
		
		TransferOneRequest request = (TransferOneRequest)message;
		StringBuilder sb = new StringBuilder(512);
		sb.append(String.format(MSG_FORMAT, request.getMessageType()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(COMPANY_ID_FORMAT,key.getCompanyId()));
		sb.append(String.format(OPID_FORMAT, key.getOpId()));
		sb.append(String.format(CALLLEG_FORMAT, request.getCallLeg()));
		sb.append(String.format(WORKID_FORMAT,request.getWorkId()));
		sb.append(String.format(NUMBER_FORMAT, request.getNumber()));
		
		return sb.toString();
	}

}
