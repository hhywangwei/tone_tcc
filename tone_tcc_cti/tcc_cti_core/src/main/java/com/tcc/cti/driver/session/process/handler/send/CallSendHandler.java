package com.tcc.cti.driver.session.process.handler.send;

import static com.tcc.cti.driver.message.MessageType.Call;

import java.io.IOException;
import java.nio.charset.Charset;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.sequence.GeneratorSeq;
import com.tcc.cti.driver.session.Phone;
import com.tcc.cti.driver.session.Sessionable;

public class CallSendHandler extends  AbstractSendHandler {
	private static final String SEQ = "0";
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return  Call.isRequest(request.getMessageType());
	}

	@Override
	public void send(Sessionable session, Requestable<? extends Response> request,
			GeneratorSeq generator,	Charset charset) throws IOException {
		
		Operator operator = session.getOperator();
		request.notifySend(operator,SEQ);
	}
	
	@Override
	protected void buildMessage(Phone phone,Requestable<? extends Response> request,
			Operator key, StringBuilder builder) {
		
		//none instance
	}

}
