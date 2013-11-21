package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.HeartbeatRequest;
import com.tcc.cti.core.message.request.RequestMessage;

public class HeartbeatSendHandlerTest {
	@Test
	public void testIsSend(){
		HeartbeatSendHandler handler = new HeartbeatSendHandler();

		Assert.assertFalse(handler.isSend(null));
		RequestMessage not = new RequestMessage("not");
		Assert.assertFalse(handler.isSend(not));
		
		HeartbeatRequest r =new HeartbeatRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		HeartbeatSendHandler handler = new HeartbeatSendHandler();
		HeartbeatRequest r =new HeartbeatRequest();
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		String m = handler.buildMessage(r, generator);
		Assert.assertEquals("<head>00013</head><msg>hb</msg>", m);
	}
}
