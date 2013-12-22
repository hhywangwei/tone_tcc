package com.tcc.cti.driver.session.process.handler.send;

import static com.tcc.cti.driver.message.MessageType.Status;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.request.StatusRequest;
import com.tcc.cti.driver.message.response.Response;

public class StatusSendHandler extends AbstractSendHandler{
	private static final String WORKID_FORMAT = "<WorkID>%s</WorkID>";
	private static final String STATUS_FORMAT = "<Status>%s</Status>";
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return Status.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			Operator key, StringBuilder builder) {
		
		StatusRequest r = (StatusRequest)request;
		buildOperator(key,builder);
		builder.append(String.format(WORKID_FORMAT, r.getWorkId()));
		builder.append(String.format(STATUS_FORMAT, r.getStatus()));
	}
}
