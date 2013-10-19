package com.tcc.cti.core.client.receive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.tcc.cti.core.client.OperatorChannel;
import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.monitor.HeartbeatKeepable;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.message.pool.CtiMessagePool;
import com.tcc.cti.core.message.response.ResponseMessage;

/**
 * {@link LoginReceiveHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 *
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
		CtiMessagePool pool = Mockito.mock(CtiMessagePool.class);
		OperatorKey key = new OperatorKey("1", "1");
		OperatorChannel oc = new OperatorChannel(key,null,new ArrayList<SendHandler>(),"UTF-8");
		Map<String,String> m = new HashMap<String,String>();
		m.put("result", "1");
		handler.receiveHandler(pool, oc, m);
		Mockito.verify(pool, Mockito.timeout(1)).put(
				Mockito.anyString(), Mockito.anyString(), Mockito.any(ResponseMessage.class));
	}
	
	@Test
	public void testReceiveHandlerButHeartbeatRegisterFail()throws Exception{
		LoginReceiveHandler handler = new LoginReceiveHandler();
		HeartbeatKeepable heartbeat = Mockito.mock(HeartbeatKeepable.class);
		Mockito.when(heartbeat.register(null)).thenReturn(false);
		handler.setHeartbeatKeep(heartbeat);
		CtiMessagePool pool = Mockito.mock(CtiMessagePool.class);
		Map<String,String> m = new HashMap<String,String>();
		m.put("result", "0");
		handler.receiveHandler(pool, null, m);
		Mockito.verify(pool, Mockito.never()).put(
				Mockito.anyString(), Mockito.anyString(), Mockito.any(ResponseMessage.class));
	}
	
	@Test
	public void testReciveHandler()throws Exception{
		CtiMessagePool pool = Mockito.mock(CtiMessagePool.class);
		OperatorKey key = new OperatorKey("1", "1");
		OperatorChannel oc = new OperatorChannel(key,null,new ArrayList<SendHandler>(),"UTF-8");
		LoginReceiveHandler handler = new LoginReceiveHandler();
		HeartbeatKeepable heartbeat = Mockito.mock(HeartbeatKeepable.class);
		Mockito.when(heartbeat.register(oc)).thenReturn(true);
		handler.setHeartbeatKeep(heartbeat);
		
		Map<String,String> m = new HashMap<String,String>();
		m.put("result", "0");
		handler.receiveHandler(pool, oc, m);
		Mockito.verify(pool, Mockito.timeout(1)).put(
				Mockito.anyString(), Mockito.anyString(), Mockito.any(ResponseMessage.class));
	}
}
