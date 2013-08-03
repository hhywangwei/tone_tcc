package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.send.LoginSendHandler;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.Login;

public class LoginSendHandlerTest {

	private final String MESSAGE = "<head>00161</head><msg>login</msg>"
			+ "<seq>1</seq><Type>1</Type><CompanyID>1</CompanyID>"
			+ "<OPID>8001</OPID><OPNumber>8002</OPNumber>"
			+ "<PassWord>c4ca4238a0b923820dcc509a6f75849b</PassWord>"; 
	@Test
	public void testGetMessage()throws Exception{
		Login login = initLoginInfo();
		LoginSendHandler send = new LoginSendHandler();
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		byte[] bytes = send.getMessage(login,generator,LoginSendHandler.DEFAULT_CHARTSET);
		Assert.assertEquals(MESSAGE, new String(bytes,LoginSendHandler.DEFAULT_CHARTSET));
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
