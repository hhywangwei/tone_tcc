package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.BaseRequest;
import com.tcc.cti.core.message.request.CallRequest;
import com.tcc.cti.core.message.request.LogoutRequest;
import com.tcc.cti.core.message.request.MobileNumberCancelRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.request.RestRequest;
import com.tcc.cti.core.message.request.ResumeRequest;
import com.tcc.cti.core.message.response.Response;

public class CommonSendHandlerTest {

	@Test
	public void testIsSend(){
		CommonSendHandler handler = new CommonSendHandler();

		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		Requestable<? extends Response> r =new CallRequest();
		Assert.assertTrue(handler.isSend(r));
		
		r = new LogoutRequest();
		Assert.assertTrue(handler.isSend(r));
		
		r = new MobileNumberCancelRequest();
		Assert.assertTrue(handler.isSend(r));
		
		r = new RestRequest();
		Assert.assertTrue(handler.isSend(r));
		
		r = new ResumeRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		CallRequest request = new CallRequest();
		
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		
		CommonSendHandler handler = new CommonSendHandler();
		OperatorKey key = new OperatorKey("1","2");
		StringBuilder builder = new StringBuilder();
		handler.buildMessage(request,key, builder);
		String e = "<CompanyID>1</CompanyID><OPID>2</OPID>";
		Assert.assertEquals(e, builder.toString());
	}
}
