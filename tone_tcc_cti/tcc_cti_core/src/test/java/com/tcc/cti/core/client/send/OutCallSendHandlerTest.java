package com.tcc.cti.core.client.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.BaseRequest;
import com.tcc.cti.core.message.request.OutCallRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

/**
 * {@link OutCallSendHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallSendHandlerTest {

	@Test
	public void testIsSend(){
		
		OutCallSendHandler handler = new OutCallSendHandler();
		
		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		OutCallRequest request = new OutCallRequest();
		handler.isSend(request);
	}
	
	@Test
	public void testBuildMessage(){
		OutCallSendHandler handler = new OutCallSendHandler();
		
		OutCallRequest request = initRequest();
		OperatorKey key = new OperatorKey("1","2");
		StringBuilder builder = new StringBuilder();
		handler.buildMessage(request,key, builder);
		String e = "<CompanyID>1</CompanyID><OPID>2</OPID><Phone1>10</Phone1><Phone2>13879201178</Phone2>";
		Assert.assertEquals(e,builder.toString());
	}
	
	private OutCallRequest initRequest(){
		OutCallRequest request =new OutCallRequest();
		
		request.setOpNumber("10");
		request.setPhone("13879201178");
		return request;
	}
}
