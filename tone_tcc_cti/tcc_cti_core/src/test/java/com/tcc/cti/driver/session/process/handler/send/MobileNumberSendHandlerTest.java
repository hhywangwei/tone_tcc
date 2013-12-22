package com.tcc.cti.driver.session.process.handler.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.BaseRequest;
import com.tcc.cti.driver.message.request.MobileNumberRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.process.handler.send.MobileNumberSendHandler;

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
		Operator key = new Operator("1","8001");
		handler.buildMessage(request,key, builder);
		Assert.assertEquals(e, builder.toString());
	}
	
	private MobileNumberRequest initRequest(){
		MobileNumberRequest request = new MobileNumberRequest();
		request.setNumber("2222-333");
		
		return request;
	}
}
