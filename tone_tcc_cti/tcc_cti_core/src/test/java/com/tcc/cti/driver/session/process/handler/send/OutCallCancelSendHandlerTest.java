package com.tcc.cti.driver.session.process.handler.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.BaseRequest;
import com.tcc.cti.driver.message.request.OutCallCancelRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.sequence.GeneratorSeq;
import com.tcc.cti.driver.session.process.handler.send.OutCallCancelSendHandler;

/**
 * {@link OutCallCancelSendHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallCancelSendHandlerTest {
	
	@Test
	public void testIsSend(){
		
		OutCallCancelSendHandler handler = new OutCallCancelSendHandler();
		
		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		OutCallCancelRequest request = new OutCallCancelRequest();
		handler.isSend(request);
	}
	
	@Test
	public void testBuildMessage(){
		OutCallCancelSendHandler handler = new OutCallCancelSendHandler();
		
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		
		OutCallCancelRequest request = initRequest();
		Operator key = new Operator("1","2");
		StringBuilder builder = new StringBuilder();
		handler.buildMessage(request,key, builder);
		String e = "<CompanyID>1</CompanyID><OPID>2</OPID><CallLeg>11_22_123</CallLeg>";
		Assert.assertEquals(e,builder.toString());
	}
	
	private OutCallCancelRequest initRequest(){
		OutCallCancelRequest request =new OutCallCancelRequest();
		
		request.setCallLeg("11_22_123");
		return request;
	}
}