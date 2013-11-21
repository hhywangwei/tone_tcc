package com.tcc.cti.core.client.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tcc.cti.core.client.OperatorChannel;
import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.model.ServerConfigure;

public class NioConnectionTest {
	private ServerConfigure _configure;
	
	@Before
	public void before()throws Exception{
		_configure = new ServerConfigure();
		_configure.setHost("211.136.173.132");
		_configure.setPort(9999);
	}
	
	@Test
	public void testWaitConnection()throws Exception{
		OperatorKey key = new OperatorKey("1", "8001");
		
		InetSocketAddress address = new InetSocketAddress(
				_configure.getHost(), _configure.getPort());
		SocketChannel channel = SocketChannel.open();
		Selector selector = Selector.open();
		
		OperatorChannel oc = new OperatorChannel.Builder(key,channel,null).build();
		
		Connectionable conn = new NioConnection(selector,address);
		boolean connection = conn.connect(oc);
		
		Assert.assertTrue(connection);
	}
	
	@Test(expected= IOException.class)
	public void testWaitConnectionAddressError()throws Exception{
		ServerConfigure configure = new ServerConfigure();
		configure.setHost("211.136.173.134");
		configure.setPort(9999);
		
		OperatorKey key = new OperatorKey("1", "8001");
		InetSocketAddress address = new InetSocketAddress(
				configure.getHost(), configure.getPort());
		SocketChannel channel = SocketChannel.open();
		Selector selector = Selector.open();
		OperatorChannel oc = new OperatorChannel.Builder(key,channel,null).build();
		
		Connectionable conn = new NioConnection(selector,address);
		conn.connect(oc);
		
		Assert.fail("Connection not exception");
	}
	
	@Test
	public void testWaitConnectionTimeOut()throws Exception{
		ServerConfigure configure = new ServerConfigure();
		configure.setHost("211.136.173.134");
		configure.setPort(9999);
		
		OperatorKey key = new OperatorKey("1", "8001");
		InetSocketAddress address = new InetSocketAddress(
				configure.getHost(), configure.getPort());
		SocketChannel channel = SocketChannel.open();
		Selector selector = Selector.open();
		OperatorChannel oc = new OperatorChannel.Builder(key,channel,null).build();
		
		Connectionable conn = new NioConnection(selector,address,10);
		boolean c = conn.connect(oc);
		Assert.assertFalse(c);
	}
}
