package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.HeartbeatRequest;
import com.tcc.cti.core.message.request.BaseRequest;

public class HeartbeatSendHandlerTest {
	@Test
	public void testIsSend(){
		HeartbeatSendHandler handler = new HeartbeatSendHandler();

		Assert.assertFalse(handler.isSend(null));
		BaseRequest not = new BaseRequest("not");
		Assert.assertFalse(handler.isSend(not));
		
		HeartbeatRequest r =new HeartbeatRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testGetMessage()throws Exception{
		HeartbeatSendHandler handler = new HeartbeatSendHandler();
		HeartbeatRequest r =new HeartbeatRequest();
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		String charset = "iso-8859-1";
		OperatorKey key = new OperatorKey("1","2");
		byte[] m = handler.getMessage(r, key, generator, Charset.forName(charset));
		Assert.assertEquals("<head>00013</head><msg>hb</msg>", new String(m,charset));
	}
}
