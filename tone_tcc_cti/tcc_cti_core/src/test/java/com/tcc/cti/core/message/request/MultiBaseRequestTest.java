package com.tcc.cti.core.message.request;

import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.core.message.response.Response;

public class MultiBaseRequestTest {
	
	@Test
	public void testReceiveComplete(){
		Response response = new Response("-1","1");
		MultiBaseRequest<Response> request = new MultiBaseRequest<Response>("group");
		request.receive(response);
		Assert.assertTrue(request._complete);
	}
	
	@Test
	public void testReceiveNotComplete(){
		Response response = new Response("1","1");
		MultiBaseRequest<Response> request = new MultiBaseRequest<Response>("group");
		request.receive(response);
		Assert.assertFalse(request._complete);
	}
}
