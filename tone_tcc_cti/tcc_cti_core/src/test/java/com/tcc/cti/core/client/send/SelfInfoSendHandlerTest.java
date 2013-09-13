package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.send.SelfInfoSendMessage;
import com.tcc.cti.core.message.send.SendMessage;

/**
 * 单元测试{@link SelfInfoSendHanlder}
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class SelfInfoSendHandlerTest {

	private final String MESSAGE = "<msg>per_worker_info</msg><seq>1</seq><WorkID></WorkID>";
	
	@Test
	public void testIsSend(){
		SelfInfoSendHandler handler = new SelfInfoSendHandler();
		Assert.assertFalse(handler.isSend(null));
		
		SendMessage m = new SelfInfoSendMessage();
		Assert.assertTrue(handler.isSend(m));
		
		SendMessage not= new SendMessage("not");
		Assert.assertFalse(handler.isSend(not));
	}
	
	@Test
	public void testBuildMessage(){
		SendMessage m = new SelfInfoSendMessage();
		m.setCompayId("1");
		m.setOpId("1");
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		
		SelfInfoSendHandler handler = new SelfInfoSendHandler();
		String message = handler.buildMessage(m, generator);
		Assert.assertEquals(MESSAGE, message);
	}
}