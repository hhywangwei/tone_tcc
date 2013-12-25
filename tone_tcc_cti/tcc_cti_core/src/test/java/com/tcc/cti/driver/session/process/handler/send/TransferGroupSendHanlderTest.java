package com.tcc.cti.driver.session.process.handler.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.BaseRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.request.TransferGroupRequest;
import com.tcc.cti.driver.message.response.CallResponse;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;
import com.tcc.cti.driver.session.process.handler.send.TransferGroupSendHanlder;

public class TransferGroupSendHanlderTest {
	@Test
	public void testIsSend(){
		TransferGroupSendHanlder handler = new TransferGroupSendHanlder();
		
		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		TransferGroupRequest r = new TransferGroupRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		TransferGroupRequest request = initRequest();
		
		TransferGroupSendHanlder handler = new TransferGroupSendHanlder();
		StringBuilder builder = new StringBuilder();
		String e = "<CompanyID>1</CompanyID><OPID>8001</OPID><CallLeg>222-333</CallLeg><GroupID>222</GroupID>";
		Operator key = new Operator("1","8001");
		Phone phone = initPhone();
		handler.buildMessage(phone,request,key, builder);
		Assert.assertEquals(e, builder.toString());
	}
	
	private Phone initPhone(){
		Phone phone = new Phone();
		CallResponse r = new CallResponse.Builder("0")
		.setCallLeg("222-333").build();
		phone.calling(r);
		
		return phone;
	}
	
	private TransferGroupRequest initRequest(){
		TransferGroupRequest request = new TransferGroupRequest();
		request.setGroupId("222");
		
		return request;
	}
}
