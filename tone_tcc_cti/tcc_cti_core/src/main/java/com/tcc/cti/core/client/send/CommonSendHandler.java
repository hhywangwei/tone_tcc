package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Call;
import static com.tcc.cti.core.message.MessageType.Logout;
import static com.tcc.cti.core.message.MessageType.MobileNumberCancel;
import static com.tcc.cti.core.message.MessageType.Rest;
import static com.tcc.cti.core.message.MessageType.Resume;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

public class CommonSendHandler extends AbstractSendHandler{

	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return Call.isRequest(request.getMessageType()) ||
				Logout.isRequest(request.getMessageType()) ||
				MobileNumberCancel.isRequest(request.getMessageType()) ||
				Rest.isRequest(request.getMessageType()) ||
				Resume.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			OperatorKey key, StringBuilder builder) {
		
		buildOperator(key,builder);
	}

}
