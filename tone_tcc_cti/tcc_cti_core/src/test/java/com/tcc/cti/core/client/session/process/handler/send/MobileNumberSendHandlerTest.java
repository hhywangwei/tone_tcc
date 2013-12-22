package com.tcc.cti.core.client.session.process.handler.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.session.process.handler.send.MobileNumberSendHandler;
import com.tcc.cti.core.message.request.BaseRequest;
import com.tcc.cti.core.message.request.MobileNumberRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

public class MobileNumberSendHandlerTest {
	@Test
	public void testIsSend(){
		MobileNumberSendHandler handler = new MobileNumberSendHandler();
		
		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		MobileNumberRequest r = new MobileNumberRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		MobileNumberRequest request = initRequest();
		
		MobileNumberSendHandler handler = new MobileNumberSendHandler();
		StringBuilder builder = new StringBuilder();
		String e = "<CompanyID>1</CompanyID><OPID>8001</OPID><MobileNumber>2222-333</MobileNumber>";
		OperatorKey key = new OperatorKey("1","8001");
		handler.buildMessage(request,key, builder);
		Assert.assertEquals(e, builder.toString());
	}
	
	private MobileNumberRequest initRequest(){
		MobileNumberRequest request = new MobileNumberRequest();
		request.setNumber("2222-333");
		
		return request;
	}
}
