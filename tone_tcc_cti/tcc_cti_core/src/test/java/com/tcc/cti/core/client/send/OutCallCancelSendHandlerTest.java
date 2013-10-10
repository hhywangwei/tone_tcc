package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.OutCallCancelRequest;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * {@link OutCallCancelSendHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallCancelSendHandlerTest {
	
	@Test
	public void testIsSend(){
		
		OutCallCancelSendHandler handler = new OutCallCancelSendHandler();
		handler.isSend(null);
		
		RequestMessage not = new RequestMessage("not");
		handler.isSend(not);
		
		OutCallCancelRequest request = new OutCallCancelRequest();
		handler.isSend(request);
	}
	
	@Test
	public void testBuildMessage(){
		OutCallCancelSendHandler handler = new OutCallCancelSendHandler();
		
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		
		OutCallCancelRequest request = initRequest();
		String m = handler.buildMessage(request, generator);
		Assert.assertNotNull(m);
		
		String e = "<msg>outcallcancel</msg><seq>1</seq><CompanyID>1</CompanyID><OPID>2</OPID><CallLeg>11_22_123</CallLeg>";
		Assert.assertEquals(e,m);
	}
	
	private OutCallCancelRequest initRequest(){
		OutCallCancelRequest request =new OutCallCancelRequest();
		
		request.setCompayId("1");
		request.setOpId("2");
		request.setCallLeg("11_22_123");
		return request;
	}
}
