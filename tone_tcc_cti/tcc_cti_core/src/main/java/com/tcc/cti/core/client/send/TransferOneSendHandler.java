package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.TransferOne;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.request.TransferOneRequest;
import com.tcc.cti.core.message.response.Response;

public class TransferOneSendHandler extends AbstractSendHandler {
	private static final String CALLLEG_FORMAT = "<CallLeg>%s</CallLeg>";
	private static final String WORKID_FORMAT = "<WorkID>%s</WorkID>";
	private static final String NUMBER_FORMAT = "<Number>%s</Number>";
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return TransferOne.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			OperatorKey key, StringBuilder builder) {
		
		TransferOneRequest r = (TransferOneRequest)request;
		buildOperator(key,builder);
		builder.append(String.format(CALLLEG_FORMAT, r.getCallLeg()));
		builder.append(String.format(WORKID_FORMAT,r.getWorkId()));
		builder.append(String.format(NUMBER_FORMAT, r.getNumber()));
	}
}
