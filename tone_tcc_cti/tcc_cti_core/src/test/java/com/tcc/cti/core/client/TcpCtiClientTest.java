package com.tcc.cti.core.client;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tcc.cti.core.client.receive.LoginReceiveHandler;
import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.send.LoginSendHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.message.pool.NoneMessagePool;
import com.tcc.cti.core.message.request.LoginRequest;
import com.tcc.cti.core.message.request.RequestMessage;

public class TcpCtiClientTest {
	
	private Configure _configure;
	
	@Before
	public void before()throws Exception{
		_configure = new Configure();
		_configure.setHost("211.136.173.132");
		_configure.setPort(9999);
	}
	
	@Test
	public void testSend()throws Exception{
		TcpCtiClient client = new TcpCtiClient(_configure,new NoneMessagePool());
		List<SendHandler> sendHandlers = new ArrayList<SendHandler>();
		sendHandlers.add(new LoginSendHandler());
		client.setSendHandlers(sendHandlers);
		List<ReceiveHandler> receiveHandlers = new ArrayList<ReceiveHandler>();
		receiveHandlers.add(new LoginReceiveHandler());
		client.setReceiveHandlers(receiveHandlers);
		client.start();
		
		RequestMessage login = initLoginInfo();
		client.register(login.getCompayId(), login.getOpId());
		client.send(login);
		Thread.sleep(3000);
		client.close();
	}

    private RequestMessage initLoginInfo(){
		
		LoginRequest login = new LoginRequest();
		login.setCompayId("1");
		login.setOpId("8002");
		login.setOpNumber("8002");
		login.setPassword("1");
		login.setType("1");
		
		return login;
	}
}