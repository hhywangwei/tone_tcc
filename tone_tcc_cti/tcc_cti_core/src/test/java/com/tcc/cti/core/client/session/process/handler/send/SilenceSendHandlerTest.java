package com.tcc.cti.core.client.session.process.handler.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.session.process.handler.send.SilenceSendHandler;
import com.tcc.cti.core.message.request.BaseRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.request.SilenceRequest;
import com.tcc.cti.core.message.response.Response;

public class SilenceSendHandlerTest {
	@Test
	public void testIsSend(){
		SilenceSendHandler handler = new SilenceSendHandler();
		
		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		SilenceRequest r = new SilenceRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		SilenceRequest request = initRequest();
		
		SilenceSendHandler handler = new SilenceSendHandler();
		StringBuilder builder = new StringBuilder();
		String e = "<CompanyID>1</CompanyID><OPID>8001</OPID><CallLeg>12-33</CallLeg><Flag>1</Flag>";
		OperatorKey key = new OperatorKey("1","8001");
		handler.buildMessage(request,key, builder);
		Assert.assertEquals(e, builder.toString());
	}
	
	private SilenceRequest initRequest(){
		SilenceRequest request = new SilenceRequest();
		request.setCallLeg("12-33");
		request.setFlag("1");
		
		return request;
	}
}
