package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.OutCallRequest;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * {@link OutCallSendHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallSendHandlerTest {

	@Test
	public void testIsSend(){
		
		OutCallSendHandler handler = new OutCallSendHandler();
		handler.isSend(null);
		
		RequestMessage not = new RequestMessage("not");
		handler.isSend(not);
		
		OutCallRequest request = new OutCallRequest();
		handler.isSend(request);
	}
	
	@Test
	public void testBuildMessage(){
		OutCallSendHandler handler = new OutCallSendHandler();
		
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		
		OutCallRequest request = initRequest();
		OperatorKey key = new OperatorKey("1","2");
		String m = handler.buildMessage(request,key, generator);
		Assert.assertNotNull(m);
		
		String e = "<msg>outcall</msg><seq>1</seq><CompanyID>1</CompanyID><OPID>2</OPID><Phone1>10</Phone1><Phone2>13879201178</Phone2>";
		Assert.assertEquals(e,m);
	}
	
	private OutCallRequest initRequest(){
		OutCallRequest request =new OutCallRequest();
		
		request.setOpNumber("10");
		request.setPhone("13879201178");
		return request;
	}
}
