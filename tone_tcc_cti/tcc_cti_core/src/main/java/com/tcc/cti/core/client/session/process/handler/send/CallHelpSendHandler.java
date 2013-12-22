package com.tcc.cti.core.client.session.process.handler.send;

import static com.tcc.cti.core.message.MessageType.CallHelp;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.CallHelpRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

public class CallHelpSendHandler extends AbstractSendHandler{
	private static final String CALLLEG_FORMAT = "<CallLeg>%s</CallLeg>";
	private static final String TRANSFER_WORKID_FORMAT = "<TransferWorkID>%s</TransferWorkID>";
	private static final String TRANSFER_NUMBER_FORMAT = "<TransferNumber>%s</TransferNumber>";
	private static final String STATUS_FORMAT = "<Status>%s</Status>";
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return CallHelp.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			OperatorKey key,StringBuilder builder) {
		
		CallHelpRequest r = (CallHelpRequest)request;
		buildOperator(key,builder);
		builder.append(String.format(CALLLEG_FORMAT, r.getCallLeg()));
		builder.append(String.format(TRANSFER_WORKID_FORMAT,r.getTransferWorkId()));
		builder.append(String.format(TRANSFER_NUMBER_FORMAT, r.getTransferNumber()));
		builder.append(String.format(STATUS_FORMAT, r.getStatus()));
	}

}
