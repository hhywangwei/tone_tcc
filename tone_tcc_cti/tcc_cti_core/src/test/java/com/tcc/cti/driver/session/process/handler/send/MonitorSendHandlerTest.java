package com.tcc.cti.driver.session.process.handler.send;

import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.BaseRequest;
import com.tcc.cti.driver.message.request.MonitorRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.process.handler.send.MonitorSendHandler;

/**
 * {@link MonitorSendHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class MonitorSendHandlerTest {

	@Test
	public void testIsSend(){
		MonitorSendHandler handler = new MonitorSendHandler();
		
		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		Assert.assertTrue(handler.isSend(new MonitorRequest()));
	}
	
	@Test
	public void testBuildMessage(){
		MonitorRequest request = new MonitorRequest();
		
		MonitorSendHandler handler = new MonitorSendHandler();	
		Operator key = new Operator("1","8001");
		StringBuilder builder = new StringBuilder();
		handler.buildMessage(request,key, builder);
		String e = "<CompanyID>1</CompanyID>";
		Assert.assertEquals(e, builder.toString());
	}
}
