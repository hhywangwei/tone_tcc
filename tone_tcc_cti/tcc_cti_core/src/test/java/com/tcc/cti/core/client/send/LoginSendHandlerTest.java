package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.send.LoginSendHandler;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.send.LoginSendMessage;

/**
 * 单元测试 {@link LoginSendHandler}
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class LoginSendHandlerTest {
	private static final String DEFAULT_CHARSET_NAME = "ISO-8859-1";
	
	private final String MESSAGE = "<head>00161</head><msg>login</msg>"
			+ "<seq>1</seq><Type>1</Type><CompanyID>1</CompanyID>"
			+ "<OPID>8001</OPID><OPNumber>8002</OPNumber>"
			+ "<PassWord>28c8edde3d61a0411511d3b1866f0636</PassWord>"; 
	@Test
	public void testGetMessage()throws Exception{
		LoginSendMessage login = initLoginInfo();
		LoginSendHandler send = new LoginSendHandler();
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		byte[] bytes = send.getMessage(login,generator,DEFAULT_CHARSET_NAME);
		Assert.assertEquals(MESSAGE, new String(bytes,DEFAULT_CHARSET_NAME));
	}
	 
	private LoginSendMessage initLoginInfo(){
		
		LoginSendMessage login = new LoginSendMessage();
		login.setCompayId("1");
		login.setOpId("8001");
		login.setOpNumber("8002");
		login.setPassword("c4ca4238a0b923820dcc509a6f75849b");
		login.setType("1");
		
		return login;
	}


}
