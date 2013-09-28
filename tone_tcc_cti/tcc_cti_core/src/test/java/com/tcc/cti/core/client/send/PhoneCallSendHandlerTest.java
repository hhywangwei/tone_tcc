package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.PhoneCallRequest;
import com.tcc.cti.core.message.request.RequestMessage;

public class PhoneCallSendHandlerTest {

	@Test
	public void testIsSend(){
		PhoneCallSendHandler handler = new PhoneCallSendHandler();

		Assert.assertFalse(handler.isSend(null));
		RequestMessage not = new RequestMessage("not");
		Assert.assertFalse(handler.isSend(not));
		
		PhoneCallRequest r =new PhoneCallRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		
		PhoneCallRequest request = new PhoneCallRequest();
		request.setCompayId("1");
		request.setOpId("2");
		
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		
		PhoneCallSendHandler handler = new PhoneCallSendHandler();
		String m = handler.buildMessage(request, generator);
		
		String e = "<msg>get_call_info</msg><seq>1</seq><CompanyID>1</CompanyID><OPID>2</OPID>";
		
		Assert.assertEquals(e, m);
	}
}
