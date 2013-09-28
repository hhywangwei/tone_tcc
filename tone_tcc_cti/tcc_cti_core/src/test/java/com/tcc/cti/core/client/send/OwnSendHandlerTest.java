package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.RequestMessage;
import com.tcc.cti.core.message.request.OwnRequest;

/**
 * 单元测试{@link SelfInfoSendHanlder}
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OwnSendHandlerTest {

	private final String MESSAGE = "<msg>per_worker_info</msg><seq>1</seq><WorkID></WorkID>";
	
	@Test
	public void testIsSend(){
		OwnSendHandler handler = new OwnSendHandler();
		Assert.assertFalse(handler.isSend(null));
		
		RequestMessage m = new OwnRequest();
		Assert.assertTrue(handler.isSend(m));
		
		RequestMessage not= new RequestMessage("not");
		Assert.assertFalse(handler.isSend(not));
	}
	
	@Test
	public void testBuildMessage(){
		RequestMessage m = new OwnRequest();
		m.setCompayId("1");
		m.setOpId("1");
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		
		OwnSendHandler handler = new OwnSendHandler();
		String message = handler.buildMessage(m, generator);
		Assert.assertEquals(MESSAGE, message);
	}
}