package com.tcc.cti.core.client.session.process.handler.send;

import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.session.process.handler.send.HeartbeatSendHandler;
import com.tcc.cti.core.message.request.BaseRequest;
import com.tcc.cti.core.message.request.HeartbeatRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

public class HeartbeatSendHandlerTest {
	@Test
	public void testIsSend(){
		HeartbeatSendHandler handler = new HeartbeatSendHandler();

		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		HeartbeatRequest r =new HeartbeatRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testGetMessage()throws Exception{
		HeartbeatSendHandler handler = new HeartbeatSendHandler();
		HeartbeatRequest r =new HeartbeatRequest();
		String charset = "iso-8859-1";
		OperatorKey key = new OperatorKey("1","2");
		byte[] m = handler.getMessage(r, key, "1", Charset.forName(charset));
		Assert.assertEquals("<head>00013</head><msg>hb</msg>", new String(m,charset));
	}
}
