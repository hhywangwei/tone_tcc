package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Heartbeat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.client.session.process.Requestsable;

public class HeartbeatReceiveHandlerTest {
	@Test
	public void testIsReceive(){
		HeartbeatReceiveHandler handler = new HeartbeatReceiveHandler();
		
		Assert.assertFalse(handler.isReceive(null));
		Assert.assertFalse(handler.isReceive("not"));
		Assert.assertTrue(handler.isReceive(Heartbeat.response()));
	}
	
	@Test
	public void testRequestMessageType(){
		HeartbeatReceiveHandler handler = new HeartbeatReceiveHandler();
		Heartbeat.isRequest(handler.getRequestMessageType(null));
	}
	
	@Test
	public void testReceiveHandler(){
		HeartbeatReceiveHandler handler = new HeartbeatReceiveHandler();
		Requestsable requests = Mockito.mock(Requestsable.class);
		Sessionable session = Mockito.mock(Sessionable.class);
		
		Map<String,String> m = new HashMap<String,String>();
		handler.receiveHandler(requests, session,"heartbeat", m);
		Mockito.verify(session,Mockito.atLeast(1)).heartbeatTouch();
		
		Assert.assertFalse(session.isService());
	}
	
}
