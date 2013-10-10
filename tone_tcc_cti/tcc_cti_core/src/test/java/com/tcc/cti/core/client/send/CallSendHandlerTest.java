package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.CallRequest;
import com.tcc.cti.core.message.request.RequestMessage;

public class CallSendHandlerTest {

	@Test
	public void testIsSend(){
		CallSendHandler handler = new CallSendHandler();

		Assert.assertFalse(handler.isSend(null));
		RequestMessage not = new RequestMessage("not");
		Assert.assertFalse(handler.isSend(not));
		
		CallRequest r =new CallRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		
		CallRequest request = new CallRequest();
		request.setCompayId("1");
		request.setOpId("2");
		
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		
		CallSendHandler handler = new CallSendHandler();
		String m = handler.buildMessage(request, generator);
		
		String e = "<msg>get_call_info</msg><seq>1</seq><CompanyID>1</CompanyID><OPID>2</OPID>";
		
		Assert.assertEquals(e, m);
	}
}
