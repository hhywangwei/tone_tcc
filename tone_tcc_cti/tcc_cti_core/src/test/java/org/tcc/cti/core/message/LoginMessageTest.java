package org.tcc.cti.core.message;

import junit.framework.Assert;

import org.junit.Test;
import org.tcc.cti.core.message.LoginMessage;
import org.tcc.cti.core.model.Login;

public class LoginMessageTest {

	private final String MESSAGE = "<head>00161</head><msg>login</msg>"
			+ "<seq>2</seq><Type>1</Type><CompanyID>1</CompanyID>"
			+ "<OPID>8001</OPID><OPNumber>8002</OPNumber>"
			+ "<PassWord>c4ca4238a0b923820dcc509a6f75849b</PassWord>"; 
	@Test
	public void testGetMessage(){
		Login login = initLoginInfo();
		LoginMessage lm = new LoginMessage(login,2l);
		String m = lm.getMessage();
		Assert.assertEquals(MESSAGE, m);
	}
	
	@Test
	public void testGetMessageByte()throws Exception{
		Login login = initLoginInfo();
		LoginMessage lm = new LoginMessage(login,2l);
		String charset = "UTF-8";
		byte[] bytes = lm.getMessage(charset);
		Assert.assertEquals(179, bytes.length);
		String s = new String(bytes,charset);
		Assert.assertEquals(MESSAGE, s);
	}
	
	@Test
	public void testGetMessageISO()throws Exception{
		Login login = initLoginInfo();
		LoginMessage lm = new LoginMessage(login,2l);
		byte[] bytes = lm.getMessageISO();
		Assert.assertEquals(179, bytes.length);
		String charset = "ISO-8859-1";
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
