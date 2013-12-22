package com.tcc.cti.core.client.receive;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.client.session.process.Requestsable;

/**
 * {@link LoginReceiveHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class LoginReceiveHandlerTest {

	@Test
	public void testIsReceive(){
		String type = "login";
		LoginReceiveHandler handler = new LoginReceiveHandler();
		Assert.assertTrue(handler.isReceive(type));
		
		type = null;
		Assert.assertFalse(handler.isReceive(type));
		
		type="login1";
		Assert.assertFalse(handler.isReceive(type));
	}
	
	@Test
	public void testReceiveHandlerButLoginFail()throws Exception{
		LoginReceiveHandler handler = new LoginReceiveHandler();
		Requestsable requests = Mockito.mock(Requestsable.class);
		Sessionable session = Mockito.mock(Sessionable.class);
		Mockito.when(session.isActive()).thenReturn(true);
		Mockito.when(session.isClose()).thenReturn(false);
		OperatorKey key = new OperatorKey("1", "1");
		Mockito.when(session.getOperatorKey()).thenReturn(key);
		
		Map<String,String> m = new HashMap<String,String>();
		m.put("result", "1");
		handler.receiveHandler(requests, session,"login", m);
		Mockito.verify(session,Mockito.atLeast(1)).login(false);
		
		Assert.assertFalse(session.isService());
		session.close();
	}
	
	@Test
	public void testReciveHandler()throws Exception{
		LoginReceiveHandler handler = new LoginReceiveHandler();
		Requestsable requests = Mockito.mock(Requestsable.class);
		Sessionable session = Mockito.mock(Sessionable.class);
		Mockito.when(session.isActive()).thenReturn(true);
		Mockito.when(session.isClose()).thenReturn(false);
		OperatorKey key = new OperatorKey("1", "1");
		Mockito.when(session.getOperatorKey()).thenReturn(key);
		
		Map<String,String> m = new HashMap<String,String>();
		m.put("result", "0");
		handler.receiveHandler(requests, session,"login", m);
		Mockito.verify(session,Mockito.atLeast(1)).login(true);
		
		session.close();
	}
}
