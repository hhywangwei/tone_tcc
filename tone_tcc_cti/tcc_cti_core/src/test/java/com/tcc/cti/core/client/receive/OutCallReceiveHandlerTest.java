package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.OutCall;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.message.response.OutCallResponse;

/**
 * {@link OutCallReceiveHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallReceiveHandlerTest {

	@Test
	public void testIsReceive(){
		OutCallReceiveHandler handler = new OutCallReceiveHandler();
		
		Assert.assertFalse(handler.isReceive(null));
		Assert.assertFalse(handler.isReceive("not"));
		Assert.assertTrue(handler.isReceive(OutCall.response()));
	}
	
	@Test
	public void testBuildMessage(){
		Map<String,String> content = initContent();
		
		OutCallReceiveHandler handler = new OutCallReceiveHandler();
		
		OutCallResponse r = (OutCallResponse) handler.buildMessage("1", "2", "3", content);
		Assert.assertEquals("1", r.getCompanyId());
		Assert.assertEquals("2", r.getOpId());
		Assert.assertEquals("3", r.getSeq());
		Assert.assertEquals("0", r.getResult());
		Assert.assertEquals(OutCall.response(), r.getMessageType());
	}
	
	private Map<String,String> initContent(){
		Map<String,String> content = new HashMap<String,String>();
		
		content.put("result", "0");
		
		return content;
	}
}
