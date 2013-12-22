package com.tcc.cti.driver.session.process.handler.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.BaseRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.request.SilenceRequest;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.process.handler.send.SilenceSendHandler;

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
		Operator key = new Operator("1","8001");
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
