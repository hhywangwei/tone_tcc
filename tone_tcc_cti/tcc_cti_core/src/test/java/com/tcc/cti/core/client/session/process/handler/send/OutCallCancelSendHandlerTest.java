package com.tcc.cti.core.client.session.process.handler.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.client.session.process.handler.send.OutCallCancelSendHandler;
import com.tcc.cti.core.message.request.BaseRequest;
import com.tcc.cti.core.message.request.OutCallCancelRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

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
		OperatorKey key = new OperatorKey("1","2");
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
