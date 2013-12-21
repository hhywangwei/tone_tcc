package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.BaseRequest;
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
		
		BaseRequest m = new OwnRequest();
		Assert.assertTrue(handler.isSend(m));
		
		BaseRequest not= new BaseRequest("not");
		Assert.assertFalse(handler.isSend(not));
	}
	
	@Test
	public void testBuildMessage(){
		BaseRequest m = new OwnRequest();
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		
		OwnSendHandler handler = new OwnSendHandler();
		OperatorKey key = new OperatorKey("1","1");
		String message = handler.buildMessage(m,key, generator);
		Assert.assertEquals(MESSAGE, message);
	}
}