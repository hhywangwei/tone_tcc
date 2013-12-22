package com.tcc.cti.core.client.session.process.handler.send;

import static com.tcc.cti.core.message.MessageType.CallHold;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.CallHoldRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

public class CallHoldSendHandler extends AbstractSendHandler{
	private static final String CALLLEG_FORMAT = "<CallLeg>%s</CallLeg>";
	private static final String FLAG_FORMAT = "<Flag>%s</Flag>";
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return CallHold.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			OperatorKey key,StringBuilder builder) {
		
		CallHoldRequest r = (CallHoldRequest)request;
		buildOperator(key,builder);
		builder.append(String.format(CALLLEG_FORMAT, r.getCallLeg()));
		builder.append(String.format(FLAG_FORMAT, r.getFlag()));
	}
}
