package com.tcc.cti.core.client.send;

import java.nio.charset.Charset;

import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.send.SendMessage;

/**
 * 单元测试{@link AbstractSendHandler}
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class AbstractSendHanlderTest {
	
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
		byte[] head = handler.headCompletion(m, charset);
		Assert.assertEquals("<head>00005</head>ddddd", new String(head,Charset.forName(charset)));
	}

	private class SendHandlerImpl extends AbstractSendHandler{

		@Override
		protected boolean isSend(SendMessage message) {
			//none instance
			return false;
		}

		@Override
		protected String buildMessage(SendMessage message,
				GeneratorSeq generator) {
			//none instance
			return null;
		}
		
	}
}