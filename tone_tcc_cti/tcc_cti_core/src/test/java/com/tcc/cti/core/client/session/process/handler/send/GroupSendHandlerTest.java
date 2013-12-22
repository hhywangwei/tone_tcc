package com.tcc.cti.core.client.session.process.handler.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.session.process.handler.send.GroupSendHandler;
import com.tcc.cti.core.message.request.BaseRequest;
import com.tcc.cti.core.message.request.GroupRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

public class GroupSendHandlerTest {

	@Test
	public void testIsSend(){
		GroupSendHandler handler = new GroupSendHandler();
		
		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		GroupRequest r = new GroupRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		GroupRequest request = new GroupRequest();
		
		GroupSendHandler handler = new GroupSendHandler();
		StringBuilder builder = new StringBuilder();
		String e = "<CompanyID>1</CompanyID>";
		OperatorKey key = new OperatorKey("1","2");
		handler.buildMessage(request,key, builder);
		Assert.assertEquals(e, builder.toString());
		
		builder = new StringBuilder();
		request.setGroupId("1");
		e = "<CompanyID>1</CompanyID><GroupID>1</GroupID>";
		handler.buildMessage(request,key, builder);
		Assert.assertEquals(e, builder.toString());
	}
}
