package com.tcc.cti.driver.session.process.handler.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.BaseRequest;
import com.tcc.cti.driver.message.request.CallHelpRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.process.handler.send.CallHelpSendHandler;

public class CallHelpSendHandlerTest {
	@Test
	public void testIsSend(){
		CallHelpSendHandler handler = new CallHelpSendHandler();
		
		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		CallHelpRequest r = new CallHelpRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		CallHelpRequest request = initRequest();
		
		CallHelpSendHandler handler = new CallHelpSendHandler();
		StringBuilder builder = new StringBuilder();
		String e = "<CompanyID>1</CompanyID><OPID>8001</OPID>"
				+ "<CallLeg>111-111</CallLeg><TransferWorkID>333</TransferWorkID>"
				+ "<TransferNumber>222</TransferNumber><Status>2</Status>"; 
		Operator key = new Operator("1","8001");
		handler.buildMessage(request,key, builder);
		Assert.assertEquals(e, builder.toString());
	}
	
	private CallHelpRequest initRequest(){
		CallHelpRequest request = new CallHelpRequest();
		request.setCallLeg("111-111");
		request.setStatus("2");
		request.setTransferNumber("222");
		request.setTransferWorkId("333");
		
		return request;
	}
}
