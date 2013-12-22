package com.tcc.cti.driver.session.process.handler.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.BaseRequest;
import com.tcc.cti.driver.message.request.GroupRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.process.handler.send.GroupSendHandler;

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
		Operator key = new Operator("1","2");
		handler.buildMessage(request,key, builder);
		Assert.assertEquals(e, builder.toString());
		
		builder = new StringBuilder();
		request.setGroupId("1");
		e = "<CompanyID>1</CompanyID><GroupID>1</GroupID>";
		handler.buildMessage(request,key, builder);
		Assert.assertEquals(e, builder.toString());
	}
}
