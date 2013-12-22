package com.tcc.cti.core.client.session.process.handler.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.session.process.handler.send.StatusSendHandler;
import com.tcc.cti.core.message.request.BaseRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.request.StatusRequest;
import com.tcc.cti.core.message.response.Response;

public class StatusSendHandlerTest {
	@Test
	public void testIsSend(){
		StatusSendHandler handler = new StatusSendHandler();
		
		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		StatusRequest r = new StatusRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		StatusRequest request = initRequest();
		
		StatusSendHandler handler = new StatusSendHandler();
		StringBuilder builder = new StringBuilder();
		String e = "<CompanyID>1</CompanyID><OPID>8001</OPID><WorkID>1111</WorkID><Status>1</Status>";
		OperatorKey key = new OperatorKey("1","8001");
		handler.buildMessage(request,key, builder);
		Assert.assertEquals(e, builder.toString());
	}
	
	private StatusRequest initRequest(){
		StatusRequest request = new StatusRequest();
		request.setStatus("1");
		request.setWorkId("1111");
		
		return request;
	}
}
