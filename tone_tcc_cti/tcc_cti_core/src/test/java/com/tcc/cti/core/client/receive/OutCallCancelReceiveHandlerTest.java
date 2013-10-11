package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.OutCallCancel;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.message.response.OutCallCancelResponse;

public class OutCallCancelReceiveHandlerTest {
	@Test
	public void testIsReceive(){
		OutCallCancelReceiveHandler handler = new OutCallCancelReceiveHandler();
		
		Assert.assertFalse(handler.isReceive(null));
		Assert.assertFalse(handler.isReceive("not"));
		Assert.assertTrue(handler.isReceive(OutCallCancel.response()));
	}
	
	@Test
	public void testBuildMessage(){
		Map<String,String> content = initContent();
		
		OutCallCancelReceiveHandler handler = new OutCallCancelReceiveHandler();
		
		OutCallCancelResponse r = (OutCallCancelResponse) handler.buildMessage("1", "2", "3", content);
		Assert.assertEquals("1", r.getCompanyId());
		Assert.assertEquals("2", r.getOpId());
		Assert.assertEquals("3", r.getSeq());
		Assert.assertEquals("0", r.getResult());
		Assert.assertEquals(OutCallCancel.response(), r.getMessageType());
	}
	
	private Map<String,String> initContent(){
		Map<String,String> content = new HashMap<String,String>();
		
		content.put("result", "0");
		
		return content;
	}
}
