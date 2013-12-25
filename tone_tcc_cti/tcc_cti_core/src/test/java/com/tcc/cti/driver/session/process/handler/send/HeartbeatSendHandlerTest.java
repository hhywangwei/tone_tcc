package com.tcc.cti.driver.session.process.handler.send;

import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.BaseRequest;
import com.tcc.cti.driver.message.request.HeartbeatRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;
import com.tcc.cti.driver.session.process.handler.send.HeartbeatSendHandler;

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
		Operator key = new Operator("1","2");
		Phone phone = new Phone();
		byte[] m = handler.getMessage(phone,r, key, "1", Charset.forName(charset));
		Assert.assertEquals("<head>00013</head><msg>hb</msg>", new String(m,charset));
	}
}
