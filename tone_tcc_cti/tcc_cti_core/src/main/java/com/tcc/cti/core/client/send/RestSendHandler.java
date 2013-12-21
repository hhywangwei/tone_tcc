package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Rest;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

public class RestSendHandler extends AbstractSendHandler {

	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return Rest.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			OperatorKey key, StringBuilder builder) {
		
		buildOperator(key, builder);
	}

}
