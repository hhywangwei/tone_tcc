package com.tcc.cti.core.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tcc.cti.core.client.TcpCtiClient;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.client.send.LoginSendHandler;
import com.tcc.cti.core.message.Login;
import com.tcc.cti.core.model.ServerConfigure;

public class TcpCtiClientTest {
	
	private TcpCtiClient client;
	
	@Before
	public void before()throws Exception{
		ServerConfigure configure = new ServerConfigure();
		configure.setHost("211.136.173.135");
		configure.setPort(9999);
		client = new TcpCtiClient("1","8001",configure);
		client.start();
	}
	
	@After
	public void after()throws Exception{
		client.close();
	}
	
	@Test
	public void testSend()throws Exception{
//		Login login = initLoginInfo();
//		SendHandler message = new LoginSendHandler(login,1l);
//		client.send(message);
//		Thread.sleep(3000);
	}

    private Login initLoginInfo(){
		
		Login login = new Login();
		login.setCompayId("1");
		login.setOpId("8002");
		login.setOpNumber("8002");
		login.setPassword("c4ca4238a0b923820dcc509a6f75849b");
		login.setType("1");
		
		return login;
	}
}
