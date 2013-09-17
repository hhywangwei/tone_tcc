package com.tcc.cti.core.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tcc.cti.core.client.receive.LoginReceiveHandler;
import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.send.LoginSendHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.message.pool.NoneMessagePool;
import com.tcc.cti.core.message.request.LoginRequest;
import com.tcc.cti.core.message.request.RequestMessage;
import com.tcc.cti.core.model.ServerConfigure;

public class TcpCtiClientTest {
	
	private ServerConfigure _configure;
	
	@Before
	public void before()throws Exception{
		_configure = new ServerConfigure();
		_configure.setHost("211.136.173.132");
		_configure.setPort(9999);
	}
	
	@Test
	public void testWaitConnection()throws Exception{
		TcpCtiClient client = new TcpCtiClient(_configure,new NoneMessagePool());
		OperatorChannel.OperatorKey key = new OperatorChannel.OperatorKey("1", "8001");
		
		InetSocketAddress address = new InetSocketAddress(
				_configure.getHost(), _configure.getPort());
		SocketChannel channel = SocketChannel.open();
		Selector selector = Selector.open();
		boolean connection = client.waitConnection(key, channel,selector,address);
		Assert.assertTrue(connection);
	}
	
	@Test(expected= IOException.class)
	public void testWaitConnectionAddressError()throws Exception{
		ServerConfigure configure = new ServerConfigure();
		configure.setHost("211.136.173.134");
		configure.setPort(9999);
		
		TcpCtiClient client = new TcpCtiClient(configure,new NoneMessagePool());
		OperatorChannel.OperatorKey key = new OperatorChannel.OperatorKey("1", "8001");
		InetSocketAddress address = new InetSocketAddress(
				configure.getHost(), configure.getPort());
		SocketChannel channel = SocketChannel.open();
		Selector selector = Selector.open();
		client.waitConnection(key, channel,selector,address);
		Assert.fail("Connection not exception");
	}
	
	@Test
	public void testWaitConnectionTimeOut()throws Exception{
		ServerConfigure configure = new ServerConfigure();
		configure.setHost("211.136.173.134");
		configure.setPort(9999);
		
		TcpCtiClient client = new TcpCtiClient(configure,new NoneMessagePool());
		client.setTimeOut(10);
		OperatorChannel.OperatorKey key = new OperatorChannel.OperatorKey("1", "8001");
		InetSocketAddress address = new InetSocketAddress(
				configure.getHost(), configure.getPort());
		SocketChannel channel = SocketChannel.open();
		Selector selector = Selector.open();
		boolean connection = client.waitConnection(key, channel,selector,address);
		Assert.assertFalse(connection);
	}
	
//	@After
//	public void after()throws Exception{
//		client.close();
//	}
//	
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