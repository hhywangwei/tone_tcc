package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Group;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.message.response.GroupResponse;

/**
 * {@link GroupReceiveHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">WangwWei</a>
 */
public class GroupReceiveHandlerTest {

	@Test
	public void testIsReceive(){
		GroupReceiveHandler handler = new GroupReceiveHandler();
		
		Assert.assertFalse(handler.isReceive(null));
		Assert.assertFalse(handler.isReceive("ddd"));
		Assert.assertTrue(handler.isReceive(Group.response()));
	}
	
	@Test
	public void testRequestMessageType(){
		GroupReceiveHandler handler = new GroupReceiveHandler();
		Group.isRequest(handler.getRequestMessageType(null));
	}
	
	@Test
	public void testBuildMessage(){
		Map<String,String> content = initContent();
		GroupReceiveHandler handler = new GroupReceiveHandler();
		
		GroupResponse r = (GroupResponse) handler.buildMessage("1", "2", "3", content);
		
		Assert.assertEquals("3", r.getSeq());
		Assert.assertEquals("0001", r.getGroupId());
		Assert.assertEquals("Test Group1", r.getGroupName());
		Assert.assertEquals("10", r.getMaxQueue());
		Assert.assertEquals("1", r.getGroupWorkState());
		Assert.assertEquals("2", r.getChooseOpType());
	}
	
	private Map<String,String> initContent(){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("GroupID", "0001");
		map.put("GroupName", "Test Group1");
		map.put("maxQueue", "10");
		map.put("Group_WorkState", "1");
		map.put("ChooseOPType", "2");
	 
		return map;
	}
}
