package com.tcc.cti.core.client.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.GroupMemberRequest;
import com.tcc.cti.core.message.request.RequestMessage;

public class GroupMemberSendHandlerTest {

	@Test
	public void testIsSend(){
		GroupMemberSendHandler handler = new GroupMemberSendHandler();
		
		Assert.assertFalse(handler.isSend(null));
		
		RequestMessage not = new RequestMessage("not");
		Assert.assertFalse(handler.isSend(not));
		
		GroupMemberRequest r = new GroupMemberRequest();
		Assert.assertTrue(handler.isSend(r));
	}
	
	@Test
	public void testBuildMessage(){
		GroupMemberRequest request = new GroupMemberRequest();
		request.setCompayId("1");
		request.setGroupId("1");
		request.setOpId("1");
		
		GeneratorSeq generator = mock(GeneratorSeq.class);
		when(generator.next()).thenReturn("1");
		
		String e = "<msg>worker_number_info</msg><seq>1</seq>"
				+ "<CompanyID>1</CompanyID><GroupID>1</GroupID>";
		
		GroupMemberSendHandler handler = new GroupMemberSendHandler();		
		String m = handler.buildMessage(request, generator);
		Assert.assertEquals(e, m);
	}

}
