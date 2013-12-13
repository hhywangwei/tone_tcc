package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.GroupRequest;
import com.tcc.cti.core.message.request.RequestMessage;

public class GroupSendHandlerTest {

	@Test
	public void testIsSend(){
		GroupSendHandler handler = new GroupSendHandler();
		
		Assert.assertFalse(handler.isSend(null));
		
		RequestMessage not = new RequestMessage("not");
		Assert.assertFalse(handler.isSend(not));
		
		GroupRequest r = new GroupRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		GroupRequest request = new GroupRequest();
		
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		
		GroupSendHandler handler = new GroupSendHandler();
		String e = "<msg>group_info</msg><seq>1</seq>"
				+ "<CompanyID>1</CompanyID>";
		OperatorKey key = new OperatorKey("1","2");
		String m = handler.buildMessage(request,key, generator);
		Assert.assertEquals(e, m);
		
		request.setGroupId("1");
		e = "<msg>group_info</msg><seq>1</seq>"
				+ "<CompanyID>1</CompanyID><GroupID>1</GroupID>";
		m = handler.buildMessage(request,key, generator);
		Assert.assertEquals(e, m);
	}
}
