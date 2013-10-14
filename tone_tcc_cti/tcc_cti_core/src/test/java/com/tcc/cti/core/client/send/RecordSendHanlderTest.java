package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.RecordRequest;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * {@link RecordSendHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class RecordSendHanlderTest {
	
	@Test
	public void testIsSend(){
		RecordSendHandler handler = new RecordSendHandler();
		Assert.assertFalse(handler.isSend(null));
		
		RequestMessage not= new RequestMessage("not");
		Assert.assertFalse(handler.isSend(not));
		
		RequestMessage m = new RecordRequest();
		Assert.assertTrue(handler.isSend(m));
	}
	
	@Test
	public void testBuildMessage(){
		RecordSendHandler handler = new RecordSendHandler();
		
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		
		RecordRequest request = initRequest();
		String m = handler.buildMessage(request, generator);
		Assert.assertNotNull(m);
		
		String e = "<msg>start_record</msg><seq>1</seq><CompanyID>1</CompanyID><OPID>2</OPID><CallLeg>111-222</CallLeg>";
		Assert.assertEquals(e,m);
	}
	
	private RecordRequest initRequest(){
		RecordRequest request =new RecordRequest();
		
		request.setCompayId("1");
		request.setOpId("2");
		request.setCallLeg("111-222");
		return request;
	}
}
