package com.tcc.cti.driver.session.process.handler.send;

import static com.tcc.cti.driver.message.MessageType.TransferOne;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.request.TransferOneRequest;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;

public class TransferOneSendHandler extends AbstractSendHandler {
	private static final String CALLLEG_FORMAT = "<CallLeg>%s</CallLeg>";
	private static final String WORKID_FORMAT = "<WorkID>%s</WorkID>";
	private static final String NUMBER_FORMAT = "<Number>%s</Number>";
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return TransferOne.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Phone phone,Requestable<? extends Response> request,
			Operator key, StringBuilder builder) {
		
		TransferOneRequest r = (TransferOneRequest)request;
		buildOperator(key,builder);
		builder.append(String.format(CALLLEG_FORMAT, phone.getCallLeg()));
		builder.append(String.format(WORKID_FORMAT,r.getWorkId()));
		builder.append(String.format(NUMBER_FORMAT, r.getNumber()));
	}
}
