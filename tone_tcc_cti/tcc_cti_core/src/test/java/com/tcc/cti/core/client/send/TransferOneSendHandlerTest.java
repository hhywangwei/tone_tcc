package com.tcc.cti.core.client.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.BaseRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.request.TransferOneRequest;
import com.tcc.cti.core.message.response.Response;

public class TransferOneSendHandlerTest {
	@Test
	public void testIsSend(){
		TransferOneSendHandler handler = new TransferOneSendHandler();
		
		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		TransferOneRequest r = new TransferOneRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		TransferOneRequest request = initRequest();
		
		TransferOneSendHandler handler = new TransferOneSendHandler();
		StringBuilder builder = new StringBuilder();
		String e = "<CompanyID>1</CompanyID><OPID>8001</OPID><CallLeg>222-333</CallLeg>"
				+ "<WorkID>23</WorkID><Number>222</Number>";
		OperatorKey key = new OperatorKey("1","8001");
		handler.buildMessage(request,key, builder);
		Assert.assertEquals(e, builder.toString());
	}
	
	private TransferOneRequest initRequest(){
		TransferOneRequest request = new TransferOneRequest();
		request.setCallLeg("222-333");
		request.setNumber("222");
		request.setWorkId("23");
		
		return request;
	}
}
