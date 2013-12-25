package com.tcc.cti.driver.session.process.handler.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.BaseRequest;
import com.tcc.cti.driver.message.request.LoginRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;
import com.tcc.cti.driver.session.process.handler.send.LoginSendHandler;

/**
 * 单元测试 {@link LoginSendHandler}
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class LoginSendHandlerTest {
	
	@Test
	public void testIsSend(){
		LoginSendHandler handler =new LoginSendHandler();
		

		Requestable<? extends Response> not = new BaseRequest<Response>("not");
		Assert.assertFalse(handler.isSend(not));
		
		LoginRequest m = new LoginRequest();
		Assert.assertTrue(handler.isSend(m));
	}
	
	@Test
	public void testBuildMessage()throws Exception{
		LoginRequest login = initLoginInfo();
		LoginSendHandler send = new LoginSendHandler();
		Operator key = new Operator("1","8001");
		StringBuilder builder = new StringBuilder();
		Phone phone = new Phone();
		send.buildMessage(phone,login,key, builder);
		String message = "<CompanyID>1</CompanyID><OPID>8001</OPID>"
				+ "<Type>1</Type><OPNumber>8002</OPNumber>"
				+ "<PassWord>28c8edde3d61a0411511d3b1866f0636</PassWord>"; 
		Assert.assertEquals(message, builder.toString());
	}
	 
	private LoginRequest initLoginInfo(){
		
		LoginRequest login = new LoginRequest();
		login.setOpNumber("8002");
		login.setPassword("c4ca4238a0b923820dcc509a6f75849b");
		login.setType("1");
		
		return login;
	}


}
