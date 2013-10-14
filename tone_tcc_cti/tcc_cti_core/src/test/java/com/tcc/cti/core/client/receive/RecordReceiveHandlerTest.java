package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Record;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.message.response.RecordResponse;

/**
 * {@link RecordReceiveHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class RecordReceiveHandlerTest {
	
	@Test
	public void testIsReceive(){
		RecordReceiveHandler handler = new RecordReceiveHandler();
		boolean not=handler.isReceive(null);
		Assert.assertFalse(not);
		not = handler.isReceive("login");
		Assert.assertFalse(not);
		
		boolean is = handler.isReceive(Record.request());
		Assert.assertTrue(is);
	}
	
	@Test
	public void testBuildMessage(){
		Map<String,String> content = initContent();
		
		RecordReceiveHandler handler = new RecordReceiveHandler();
		
		RecordResponse r = (RecordResponse) handler.buildMessage("1", "2", "3", content);
		Assert.assertEquals("1", r.getCompanyId());
		Assert.assertEquals("2", r.getOpId());
		Assert.assertEquals("3", r.getSeq());
		Assert.assertEquals("0", r.getResult());
		Assert.assertEquals(Record.response(), r.getMessageType());
	}
	
	private Map<String,String> initContent(){
		Map<String,String> content = new HashMap<String,String>();
		
		content.put("result", "0");
		
		return content;
	}
}
