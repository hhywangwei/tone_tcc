package com.tcc.cti.core.client.send;

import java.nio.charset.Charset;

import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

/**
 * 单元测试{@link AbstractSendHandler}
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class AbstractSendHanlderTest {
	
	@Test
	public void testBuildHead(){
		SendHandlerImpl handler = new SendHandlerImpl();
		StringBuilder builder = new StringBuilder(128);
		handler.buildHead("login", "111", builder);
		String msg = "<msg>login</msg><seq>111</seq>";
		Assert.assertEquals(msg, builder.toString());
	}
	
	@Test
	public void testBuildOperator(){
		SendHandlerImpl handler = new SendHandlerImpl();
		StringBuilder builder = new StringBuilder(128);
		OperatorKey key = new OperatorKey("11","22");
		handler.buildOperator(key, builder);
		String msg = "<CompanyID>11</CompanyID><OPID>22</OPID>";
		Assert.assertEquals(msg, builder.toString());
	}
	
	@Test
	public void testGetHeadSegment(){
		SendHandlerImpl handler = new SendHandlerImpl();
		String head = handler.getHeadSegment(20);
		Assert.assertEquals("<head>00020</head>", head);
	}
	
	@Test
	public void testHeadCompletion(){
		SendHandlerImpl handler = new SendHandlerImpl();
		String m = "ddddd";
		String charset = "iso-8859-1";
		byte[] head = handler.headCompletion(m, Charset.forName(charset));
		Assert.assertEquals("<head>00005</head>ddddd", new String(head,Charset.forName(charset)));
	}

	private class SendHandlerImpl extends AbstractSendHandler{

		@Override
		protected boolean isSend(Requestable<? extends Response> message) {
			//none instance
			return false;
		}

		@Override
		protected void buildMessage(Requestable<? extends Response> message,OperatorKey key,
				StringBuilder builder) {
			//none instance
		}
	}
}
