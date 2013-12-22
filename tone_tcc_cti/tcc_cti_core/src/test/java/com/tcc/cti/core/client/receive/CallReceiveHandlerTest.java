package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Call;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.message.response.CallResponse;

/**
 * {@link CallReceiveHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class CallReceiveHandlerTest {

	@Test
	public void testIsReceive(){
		CallReceiveHandler handler = new CallReceiveHandler();
		
		Assert.assertFalse(handler.isReceive(null));
		Assert.assertFalse(handler.isReceive("not"));
		Assert.assertTrue(handler.isReceive(Call.response()));
	}
	
	@Test
	public void testRequestMessageType(){
		CallReceiveHandler handler = new CallReceiveHandler();
		Call.isRequest(handler.getRequestMessageType(null));
	}
	
	@Test
	public void testBuildMessage(){
		CallReceiveHandler handler = new CallReceiveHandler();
		Map<String,String> content = initContent();
		CallResponse message = (CallResponse)handler.buildMessage("1", "2", "3", content);
		
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
