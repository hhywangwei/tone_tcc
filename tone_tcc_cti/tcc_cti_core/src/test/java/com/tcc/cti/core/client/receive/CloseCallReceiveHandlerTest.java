package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.CloseCall;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.message.response.CloseCallResponse;
/**
 * {@link CloseCallReceiveHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class CloseCallReceiveHandlerTest {

	@Test
	public void testIsReceive(){
		CloseCallReceiveHandler handler = new CloseCallReceiveHandler();
		
		Assert.assertFalse(handler.isReceive(null));
		Assert.assertFalse(handler.isReceive("not"));
		Assert.assertTrue(handler.isReceive(CloseCall.response()));
	}
	
	@Test
	public void testBuildMessage(){
		CloseCallReceiveHandler handler = new CloseCallReceiveHandler();
		Map<String,String> content = initContent();
		CloseCallResponse message = (CloseCallResponse)handler.buildMessage("1", "2", "3", content);
		
		Assert.assertEquals(CloseCall.response(), message.getMessageType());
		Assert.assertEquals("1", message.getCompanyId());
		Assert.assertEquals("2", message.getOpId());
		Assert.assertEquals("3", message.getSeq());
		Assert.assertEquals("0001", message.getGroupId());
		Assert.assertEquals("3055_1242013662_1242704147_124", message.getCallLeg());
		Assert.assertEquals("9527", message.getCallerNumber());
		Assert.assertEquals("17920", message.getAccessNumber());
		Assert.assertEquals("8622", message.getCalledNumber());
		Assert.assertEquals("0", message.getReleaseReason());
	}
	
	private Map<String,String> initContent(){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("GroupID", "0001");
		map.put("CallLeg", "3055_1242013662_1242704147_124");
		map.put("CallerNumber", "9527");
		map.put("AccessNumber", "17920");
		map.put("CalledNumber", "8622");
		map.put("ReleaseReason", "0");
	 
		return map;
	}
}
