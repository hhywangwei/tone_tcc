package com.tcc.cti.driver.session.process.handler.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.BaseRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.request.TransferOneRequest;
import com.tcc.cti.driver.message.response.CallResponse;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;
import com.tcc.cti.driver.session.process.handler.send.TransferOneSendHandler;

public class TransferOneSendHandlerTest {
	@Test
	public void testIsSend(){
		TransferOneSendHandler handler = new TransferOneSendHandler();
		
		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		TransferOneRequest r = new TransferOneRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		TransferOneRequest request = initRequest();
		
		TransferOneSendHandler handler = new TransferOneSendHandler();
		StringBuilder builder = new StringBuilder();
		String e = "<CompanyID>1</CompanyID><OPID>8001</OPID><CallLeg>222-333</CallLeg>"
				+ "<WorkID>23</WorkID><Number>222</Number>";
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
	
	private TransferOneRequest initRequest(){
		TransferOneRequest request = new TransferOneRequest();
		request.setNumber("222");
		request.setWorkId("23");
		
		return request;
	}
}
