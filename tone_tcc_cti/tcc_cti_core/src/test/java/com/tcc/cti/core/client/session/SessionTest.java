package com.tcc.cti.core.client.session;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.connection.Connectionable;
import com.tcc.cti.core.client.heartbeat.HeartbeatKeepable;
import com.tcc.cti.core.client.heartbeat.ScheduledHeartbeatKeep;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.client.session.process.MessageProcessable;
import com.tcc.cti.core.message.request.CallRequest;
import com.tcc.cti.core.message.request.LoginRequest;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * {@link Session}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class SessionTest {
	private Session createSession(Connectionable connection){
		OperatorKey key = new OperatorKey("1","8002");
		MessageProcessable process = Mockito.mock(MessageProcessable.class);
		SendHandler noneHandler = Mockito.mock(SendHandler.class);
		return  new Session.Builder(key,connection, process,
				noneHandler, new ScheduledHeartbeatKeep()).
				build();
	}
	
	
	@Test
	public void testStartButConnectionFail()throws IOException{
		Session session = null ;
		try{
			Connectionable connection = Mockito.mock(Connectionable.class);
			session = createSession(connection);
			Mockito.when(connection.connect(session)).thenThrow(new IOException());
			session.start();
			Assert.fail();
		}catch(IOException e){
			Assert.assertTrue(session.isClose());
		}finally{
			if(session != null){
				session.close();
			}
		}
	}
	
	@Test
	public void testStart()throws IOException{
		Session session = null ;
		SocketChannel channel = SocketChannel.open();
		try{
			Connectionable connection = Mockito.mock(Connectionable.class);
			session = createSession(connection);
			Mockito.when(connection.connect(session)).thenReturn(channel);
			session.start();
			Assert.assertTrue(session.isActive());
		}finally{
			if(session != null){
				session.close();
			}
			if(channel.isOpen()){
				channel.close();
			}
		}
	}
	
	@Test
	public void testLoginButLoginFail()throws IOException{
		Session session = null ;
		try{
			Connectionable connection = Mockito.mock(Connectionable.class);
			session = createSession(connection);
			session.login(false);
			Assert.assertTrue(session.isNew());
		}finally{
			if(session != null){
				session.close();
			}
		}
	}
	
	@Test
	public void testLogin()throws IOException{
		Session session = null ;
		SocketChannel channel = SocketChannel.open();
		try{
			Connectionable connection = Mockito.mock(Connectionable.class);
			session = createSession(connection);
			Mockito.when(connection.connect(session)).thenReturn(channel);
			session.start();
			session.login(true);
			Assert.assertTrue(session.isService());
		}finally{
			if(session != null){
				session.close();
			}
			if(channel.isOpen()){
				channel.close();
			}
		}
	}
	
	@Test
	public void testLoginButClose()throws IOException{
		Session session = null ;
		SocketChannel channel = SocketChannel.open();
		try{
			Connectionable connection = Mockito.mock(Connectionable.class);
			session = createSession(connection);
			Mockito.when(connection.connect(session)).thenReturn(channel);
			session.start();
			session.close();
			session.login(true);
			Assert.assertTrue(session.isClose());
		}finally{
			if(session != null){
				session.close();
			}
			if(channel.isOpen()){
				channel.close();
			}
		}
	}
	
	private Session createSession(Connectionable connection,HeartbeatKeepable heartbeatKeep){
		OperatorKey key = new OperatorKey("1","8002");
		MessageProcessable process = Mockito.mock(MessageProcessable.class);
		SendHandler noneHandler = Mockito.mock(SendHandler.class);
		return  new Session.Builder(key,connection, process,
				noneHandler, heartbeatKeep).
				build();
	}
	
	@Test
	public void testCloseNewOfStatus()throws IOException{
		Session session = null ;
		SocketChannel channel = SocketChannel.open();
		try{
			Connectionable connection = Mockito.mock(Connectionable.class);
			HeartbeatKeepable keep = Mockito.mock(HeartbeatKeepable.class);
			session = createSession(connection,keep);
			Mockito.when(connection.connect(session)).thenReturn(channel);
			Assert.assertTrue(session.isNew());
			session.close();
			Mockito.verify(keep,Mockito.never()).shutdown();
			Assert.assertTrue(session.isClose());
			Assert.assertTrue(channel.isOpen());
		}finally{
			if(channel.isOpen()){
				channel.close();
			}
		}
	}
		
	@Test
	public void testCloseSeviceOfStatus()throws IOException{
		Session session = null ;
		SocketChannel channel = SocketChannel.open();
		try{
			Connectionable connection = Mockito.mock(Connectionable.class);
			HeartbeatKeepable keep = Mockito.mock(HeartbeatKeepable.class);
			session = createSession(connection,keep);
			Mockito.when(connection.connect(session)).thenReturn(channel);
			session.start();
			session.login(true);
			Assert.assertTrue(session.isService());
			session.close();
			Mockito.verify(keep,Mockito.atLeastOnce()).shutdown();
			Assert.assertTrue(session.isClose());
			Assert.assertFalse(channel.isOpen());
		}finally{
			if(channel.isOpen()){
				channel.close();
			}
		}
	}
	
	@Test
	public void testSendNewOfStatus()throws IOException{
		Session session = null ;
		SocketChannel channel = SocketChannel.open();
		try{
			Connectionable connection = Mockito.mock(Connectionable.class);
			session = createSession(connection);
			Mockito.when(connection.connect(session)).thenReturn(channel);
			LoginRequest r = new LoginRequest();
			session.send(r);
			Assert.assertTrue(session.isActive());
		}finally{
			if(session != null){
				session.close();
			}
			if(channel.isOpen()){
				channel.close();
			}
		}
	}
	
	@Test(expected=IOException.class)
	public void testSendCloseOfStatus()throws IOException{
		Session session = null ;
		SocketChannel channel = SocketChannel.open();
		try{
			Connectionable connection = Mockito.mock(Connectionable.class);
			session = createSession(connection);
			Mockito.when(connection.connect(session)).thenReturn(channel);
			session.close();
			LoginRequest r = new LoginRequest();
			session.send(r);
			Assert.fail();
		}finally{
			if(session != null){
				session.close();
			}
			if(channel.isOpen()){
				channel.close();
			}
		}
	}
	
	private SendHandler sendHandler ;
	
	private Session createSessionSendHandlers(Connectionable connection){
		OperatorKey key = new OperatorKey("1","8002");
		sendHandler = Mockito.mock(SendHandler.class);
		MessageProcessable process = Mockito.mock(MessageProcessable.class);
		return  new Session.Builder(key,connection, process,
				sendHandler, new ScheduledHeartbeatKeep()).
				build();
	}
	
	@Test(expected=SessionAccessException.class)
	public void testSendButNotAccess()throws IOException{
		Session session = null ;
		SocketChannel channel = SocketChannel.open();
		try{
			Connectionable connection = Mockito.mock(Connectionable.class);
			session = createSessionSendHandlers(connection);
			Mockito.when(connection.connect(session)).thenReturn(channel);
			CallRequest r = new CallRequest();
			session.send(r);
			Assert.fail();
		}finally{
			if(session != null){
				session.close();
			}
			if(channel.isOpen()){
				channel.close();
			}
		}
	}
	
	@Test
	public void testSendAccess()throws IOException{
		Session session = null ;
		SocketChannel channel = SocketChannel.open();
		try{
			Connectionable connection = Mockito.mock(Connectionable.class);
			session = createSessionSendHandlers(connection);
			Mockito.when(connection.connect(session)).thenReturn(channel);
			LoginRequest r = new LoginRequest();
			session.send(r);
			Mockito.verify(sendHandler,Mockito.atLeastOnce()).send(
					Mockito.any(SocketChannel.class),Mockito.any(OperatorKey.class), Mockito.any(RequestMessage.class) ,
					Mockito.any(GeneratorSeq.class), Mockito.any(Charset.class));
		}finally{
			if(session != null){
				session.close();
			}
			if(channel.isOpen()){
				channel.close();
			}
		}
	}
}
