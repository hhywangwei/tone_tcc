package com.tcc.cti.driver.session.process.handler.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.BaseRequest;
import com.tcc.cti.driver.message.request.CallHoldRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.process.handler.send.CallHoldSendHandler;

public class CallHoldSendHandlerTest {
	@Test
	public void testIsSend(){
		CallHoldSendHandler handler = new CallHoldSendHandler();
		
		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		CallHoldRequest r = new CallHoldRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		CallHoldRequest request = initRequest();
		
		CallHoldSendHandler handler = new CallHoldSendHandler();
		StringBuilder builder = new StringBuilder();
		String e = "<CompanyID>1</CompanyID><OPID>8001</OPID>"
				+ "<CallLeg>111-111</CallLeg><Flag>1</Flag>";
		Operator key = new Operator("1","8001");
		handler.buildMessage(request,key, builder);
		Assert.assertEquals(e, builder.toString());
	}
	
	private CallHoldRequest initRequest(){
		CallHoldRequest request = new CallHoldRequest();
		request.setCallLeg("111-111");
		request.setFlag("1");
		
		return request;
	}
}
