package com.tcc.cti.core.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tcc.cti.core.message.CtiMessage;
import com.tcc.cti.core.message.Login;

public class TcpCtiClientTest {
	
	private TcpCtiClient client;
	
	@Before
	public void before()throws Exception{
//		ServerConfigure configure = new ServerConfigure();
//		configure.setHost("211.136.173.132");
//		configure.setPort(9999);
//		client = new TcpCtiClient("1","8001",configure,new NoneMessagePool());
//		
//		List<ReceiveHandler> receives = new ArrayList<ReceiveHandler>();
//		receives.add(new LoginReceiveHandler());
//		client.setReceiveHandlers(receives);
//		
//		List<SendHandler> sends = new ArrayList<SendHandler>();
//		sends.add(new LoginSendHandler());
//		client.setSendHandlers(sends);
//		
//		client.start();
	}
	
	@After
	public void after()throws Exception{
		client.close();
	}
	
	@Test
	public void testSend()throws Exception{
		CtiMessage login = initLoginInfo();
		client.send(login);
		Thread.sleep(3000);
	}

    private CtiMessage initLoginInfo(){
		
		Login login = new Login();
		login.setCompayId("1");
		login.setOpId("8002");
		login.setOpNumber("8002");
		login.setPassword("1");
		login.setType("1");
		
		return login;
	}
}
