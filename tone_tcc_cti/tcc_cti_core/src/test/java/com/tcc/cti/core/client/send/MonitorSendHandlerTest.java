package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.MonitorRequest;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * {@link MonitorSendHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class MonitorSendHandlerTest {

	@Test
	public void testIsSend(){
		MonitorSendHandler handler = new MonitorSendHandler();
		
		Assert.assertFalse(handler.isSend(null));
		RequestMessage not = new RequestMessage("not");
		Assert.assertFalse(handler.isSend(not));
		
		Assert.assertTrue(handler.isSend(new MonitorRequest()));
	}
	
	@Test
	public void testBuildMessage(){
		MonitorRequest request = new MonitorRequest();
		request.setCompayId("1");
		
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		
		String e = "<msg>monitor_info</msg><seq>1</seq><CompanyID>1</CompanyID>";
		MonitorSendHandler handler = new MonitorSendHandler();		
		String m = handler.buildMessage(request, generator);
		Assert.assertEquals(e, m);
	}
}
