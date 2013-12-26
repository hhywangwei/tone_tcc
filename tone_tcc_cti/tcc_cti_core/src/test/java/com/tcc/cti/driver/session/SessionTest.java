package com.tcc.cti.driver.session;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.connection.Connectionable;
import com.tcc.cti.driver.heartbeat.HeartbeatKeepable;
import com.tcc.cti.driver.heartbeat.ScheduledHeartbeatKeep;
import com.tcc.cti.driver.message.request.CallRequest;
import com.tcc.cti.driver.message.request.LoginRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.CallResponse;
import com.tcc.cti.driver.sequence.GeneratorSeq;
import com.tcc.cti.driver.session.Sessionable.UpdatePhoneCallBack;
import com.tcc.cti.driver.session.process.MessageProcessable;

/**
 * {@link Session}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class SessionTest {
	private static final Logger logger= LoggerFactory.getLogger(SessionTest.class);
			
	private Session createSession(Connectionable connection){
		Operator key = new Operator("1","8002");
		MessageProcessable process = Mockito.mock(MessageProcessable.class);
		return  new Session.Builder(key,connection, process,
				 new ScheduledHeartbeatKeep()).
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
		Operator key = new Operator("1","8002");
		MessageProcessable process = Mockito.mock(MessageProcessable.class);
		return  new Session.Builder(key,connection, process,heartbeatKeep).
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
	
	private Session createSessionSendHandlers(Connectionable connection){
		Operator key = new Operator("1","8002");
		MessageProcessable process = Mockito.mock(MessageProcessable.class);
		return  new Session.Builder(key,connection, process,
				new ScheduledHeartbeatKeep()).
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
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSendAccess()throws IOException{
		Session session = null ;
		SocketChannel channel = SocketChannel.open();
		try{
			Connectionable connection = Mockito.mock(Connectionable.class);
			Operator key = new Operator("1","8002");
			MessageProcessable process = Mockito.mock(MessageProcessable.class);
			session =  new Session.Builder(key,connection, process,
					new ScheduledHeartbeatKeep()).
					build();
			Mockito.when(connection.connect(session)).thenReturn(channel);
			LoginRequest r = new LoginRequest();
			session.send(r);
			Mockito.verify(process,Mockito.atLeastOnce()).sendProcess(Mockito.any(Sessionable.class), 
					Mockito.any(Requestable.class), Mockito.any(GeneratorSeq.class));
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
	public void testIsClientRquestTimeoutIsPhoneCalling(){
		Connectionable connection = Mockito.mock(Connectionable.class);
		Session session = createSession(connection);
		session.updatePhone(new UpdatePhoneCallBack(){
			@Override
			public void update(Phone phone) {
				CallResponse response = new CallResponse.
						Builder("221").
						setCallLeg("111-2222").
						build();
				phone.calling(response);
			}
		});
		boolean timeout = session.isClientRquestTimeout();
		Assert.assertFalse(timeout);
	}
	
	@Test
	public void testIsClientRequestTimeoutIsTimeout(){
		Connectionable connection = Mockito.mock(Connectionable.class);
		Operator key = new Operator("1","8002");
		MessageProcessable process = Mockito.mock(MessageProcessable.class);
		Session session = new Session.Builder(key,connection, process,
				 new ScheduledHeartbeatKeep()).
				 setClientTimeout(1 * 1000).
				build();
		try{
			Thread.sleep( 3 * 1000);
		}catch(Exception e){
			logger.error("Sleep error is {}",e.getMessage());
			Assert.fail();
		}
		boolean timeout = session.isClientRquestTimeout();
		Assert.assertTrue(timeout);
	}
	
	@Test
	public void testIsClientRequestTimeout(){
		Connectionable connection = Mockito.mock(Connectionable.class);
		Operator key = new Operator("1","8002");
		MessageProcessable process = Mockito.mock(MessageProcessable.class);
		Session session = new Session.Builder(key,connection, process,
				 new ScheduledHeartbeatKeep()).
				 setClientTimeout(1 * 1000).
				build();
		boolean timeout = session.isClientRquestTimeout();
		Assert.assertFalse(timeout);
	}
}
