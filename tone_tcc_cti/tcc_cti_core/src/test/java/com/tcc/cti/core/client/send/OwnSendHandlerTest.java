package com.tcc.cti.core.client.send;

import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.BaseRequest;
import com.tcc.cti.core.message.request.OwnRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

/**
 * 单元测试{@link SelfInfoSendHanlder}
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OwnSendHandlerTest {

	@Test
	public void testIsSend(){
		OwnSendHandler handler = new OwnSendHandler();
		
		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		OwnRequest m = new OwnRequest();
		Assert.assertTrue(handler.isSend(m));
	}
	
	@Test
	public void testBuildMessage(){
		OwnRequest m = new OwnRequest();
		
		OwnSendHandler handler = new OwnSendHandler();
		OperatorKey key = new OperatorKey("1","1");
		StringBuilder builder = new StringBuilder();
		handler.buildMessage(m,key, builder);
		String msg = "<WorkID></WorkID>";
		Assert.assertEquals(msg, builder.toString());
	}
}