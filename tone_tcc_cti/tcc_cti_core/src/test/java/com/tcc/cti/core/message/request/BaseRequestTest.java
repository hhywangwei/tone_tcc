package com.tcc.cti.core.message.request;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.tcc.cti.core.message.RequestTimeoutException;
import com.tcc.cti.core.message.event.RequestEvent;
import com.tcc.cti.core.message.response.Response;

public class BaseRequestTest {

	@Test
	public void testGetMessageType(){
		BaseRequest<Response> request = new BaseRequest<Response>("login");
		Assert.assertEquals("login", request.getMessageType());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testNotifySend(){
		BaseRequest<Response> request = new BaseRequest<Response>("login");
		RequestEvent event = Mockito.mock(RequestEvent.class);
		request.regsiterEvent(event);
		request.notifySend("1");
		Mockito.verify(event, Mockito.atLeastOnce()).startRequest(
				Mockito.anyString(), Mockito.anyString(), Mockito.any(Requestable.class));
	}
	
	@Test
	public void testNotifySendError(){
		BaseRequest<Response> request = new BaseRequest<Response>("login");
		RequestEvent event = Mockito.mock(RequestEvent.class);
		request.regsiterEvent(event);
		request.notifySend("1");
		request.notifySendError(new Exception());
		Mockito.verify(event, Mockito.atLeastOnce()).finishRequest(
				Mockito.anyString(), Mockito.anyString());
	}
	
	@Test
	public void testReceive(){
		BaseRequest<Response> request = new BaseRequest<Response>("login");
		request.receive(new Response("1","0"));
		List<Response> list = request.responseNow();
		Assert.assertTrue(list.size() == 1);
	}
	
	@Test
	public void testResponseTimeout()throws InterruptedException{
		BaseRequest<Response> request = new BaseRequest<Response>("login");
		try{
			request.response(2*100);
			Assert.fail();
		}catch(RequestTimeoutException e){
			Assert.assertNotNull(e);
		}
	}
	
	@Test
	public void testResponse()throws InterruptedException, RequestTimeoutException{
		final BaseRequest<Response> request = new BaseRequest<Response>("login");
		request.notifySend("1");
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				try{
					Thread.sleep(2 * 1000);
					request.receive(new Response("1","1"));
				}catch(Exception e){
					Assert.fail();
				}
			}
		});
		t.start();
		List<Response> rs = request.response(5 * 1000);
		Assert.assertTrue(rs.size() == 1);
	}
}
