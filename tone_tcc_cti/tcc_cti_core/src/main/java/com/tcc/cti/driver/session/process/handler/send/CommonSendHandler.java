package com.tcc.cti.driver.session.process.handler.send;

import static com.tcc.cti.driver.message.MessageType.Call;
import static com.tcc.cti.driver.message.MessageType.Logout;
import static com.tcc.cti.driver.message.MessageType.MobileNumberCancel;
import static com.tcc.cti.driver.message.MessageType.Rest;
import static com.tcc.cti.driver.message.MessageType.Resume;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;

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
			Operator key, StringBuilder builder) {
		
		buildOperator(key,builder);
	}

}
