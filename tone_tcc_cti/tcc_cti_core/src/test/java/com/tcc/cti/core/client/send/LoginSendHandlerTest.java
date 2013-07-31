package com.tcc.cti.core.client.send;

import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.core.client.send.LoginSendHandler;
import com.tcc.cti.core.message.Login;

public class LoginSendHandlerTest {

	private final String MESSAGE = "<head>00161</head><msg>login</msg>"
			+ "<seq>1</seq><Type>1</Type><CompanyID>1</CompanyID>"
			+ "<OPID>8001</OPID><OPNumber>8002</OPNumber>"
			+ "<PassWord>c4ca4238a0b923820dcc509a6f75849b</PassWord>"; 
	@Test
	public void testGetMessage()throws Exception{
		Login login = initLoginInfo();
		LoginSendHandler lm = new LoginSendHandler();
		byte[] bytes = lm.getMessage(login,LoginSendHandler.DEFAULT_CHARTSET);
		Assert.assertEquals(MESSAGE, new String(bytes,LoginSendHandler.DEFAULT_CHARTSET));
	}
	
	@Test
	public void testGetMessageByte()throws Exception{
		Login login = initLoginInfo();
		LoginSendHandler lm = new LoginSendHandler();
		String charset = "UTF-8";
		byte[] bytes = lm.getMessage(login,charset);
		Assert.assertEquals(179, bytes.length);
		String s = new String(bytes,charset);
		Assert.assertEquals(MESSAGE, s);
	}
	 
	private Login initLoginInfo(){
		
		Login login = new Login();
		login.setCompayId("1");
		login.setOpId("8001");
		login.setOpNumber("8002");
		login.setPassword("c4ca4238a0b923820dcc509a6f75849b");
		login.setType("1");
		
		return login;
	}


}
