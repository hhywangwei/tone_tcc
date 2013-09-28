package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.PhoneCall;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.message.response.PhoneCallResponse;

/**
 * {@link PhoneCallReceiveHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class PhoneCallReceiveHandlerTest {

	@Test
	public void testIsReceive(){
		PhoneCallReceiveHandler handler = new PhoneCallReceiveHandler();
		
		Assert.assertFalse(handler.isReceive(null));
		Assert.assertFalse(handler.isReceive("not"));
		Assert.assertTrue(handler.isReceive(PhoneCall.response()));
	}
	
	@Test
	public void testBuildMessage(){
		PhoneCallReceiveHandler handler = new PhoneCallReceiveHandler();
		Map<String,String> content = initContent();
		PhoneCallResponse message = (PhoneCallResponse)handler.buildMessage("1", "2", "3", content);
		
		Assert.assertEquals("add_call", message.getMessageType());
		Assert.assertEquals("1", message.getCompanyId());
		Assert.assertEquals("2", message.getOpId());
		Assert.assertEquals("3", message.getSeq());
		Assert.assertEquals("0005", message.getGroupId());
		Assert.assertEquals("3055_1242013662_1242704147_124", message.getCallLeg());
		Assert.assertEquals("9527", message.getCallerNumber());
		Assert.assertEquals("17920", message.getAccessNumber());
		Assert.assertEquals("8622", message.getCalledNumber());
		Assert.assertEquals("1", message.getCallState());
		Assert.assertEquals("1231", message.getOpNumber());
		Assert.assertEquals("test", message.getName());
		Assert.assertEquals("123", message.getGlobalCallLeg());
		Assert.assertEquals("1", message.getRecordFlag());
		Assert.assertEquals("0", message.getCallType());
		Assert.assertEquals("02", message.getPreOpId());
		Assert.assertEquals("20", message.getPreOpNumber());
		Assert.assertEquals("test1", message.getPreOpName());
		Assert.assertEquals("1", message.getUserInput());
	}
	
	private Map<String,String> initContent(){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("GroupID", "0005");
		map.put("CallLeg", "3055_1242013662_1242704147_124");
		map.put("CallerNumber", "9527");
		map.put("AccessNumber", "17920");
		map.put("CalledNumber", "8622");
		map.put("CallState", "1");
		map.put("OPNumber", "1231");
		map.put("Name", "test");
		map.put("GlobalCallLeg", "123");
		map.put("RecordFlag", "1");
		map.put("CallType", "0");
		map.put("PreOPID", "02");
		map.put("PreOPNumber", "20");
		map.put("PreOPName", "test1");
		map.put("UserInput", "1");
		
		return map;
	}
}
