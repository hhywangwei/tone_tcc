package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.CallHelp;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.CallHelpRequest;
import com.tcc.cti.core.message.request.RequestMessage;

public class CallHelpSendHandler extends AbstractSendHandler{
	private static final String CALLLEG_FORMAT = "<CallLeg>%s</CallLeg>";
	private static final String TRANSFER_WORKID_FORMAT = "<TransferWorkID>%s</TransferWorkID>";
	private static final String TRANSFER_NUMBER_FORMAT = "<TransferNumber>%s</TransferNumber>";
	private static final String STATUS_FORMAT = "<Status>%s</Status>";
	
	@Override
	protected boolean isSend(RequestMessage message) {
		return CallHelp.isRequest(message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message, OperatorKey key,
			GeneratorSeq generator) {
		
		CallHelpRequest request = (CallHelpRequest)message;
		StringBuilder sb = new StringBuilder(512);
		sb.append(String.format(MSG_FORMAT, request.getMessageType()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(COMPANY_ID_FORMAT,key.getCompanyId()));
		sb.append(String.format(OPID_FORMAT, key.getOpId()));
		sb.append(String.format(CALLLEG_FORMAT, request.getCallLeg()));
		sb.append(String.format(TRANSFER_WORKID_FORMAT,request.getTransferWorkId()));
		sb.append(String.format(TRANSFER_NUMBER_FORMAT, request.getTransferNumber()));
		sb.append(String.format(STATUS_FORMAT, request.getStatus()));
		
		return sb.toString();
	}

}
