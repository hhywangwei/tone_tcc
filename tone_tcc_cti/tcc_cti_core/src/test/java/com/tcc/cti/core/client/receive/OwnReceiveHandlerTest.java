package com.tcc.cti.core.client.receive;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.message.MessageType;
import com.tcc.cti.core.message.response.OwnResponse;

public class OwnReceiveHandlerTest {

	@Test
	public void testIsReceive(){
		OwnReceiveHandler handler = new OwnReceiveHandler();
		boolean not=handler.isReceive(null);
		Assert.assertFalse(not);
		not = handler.isReceive("login");
		Assert.assertFalse(not);
		
		boolean is = handler.isReceive(MessageType.Own.request());
		Assert.assertTrue(is);
	}
	
	@Test
	public void testBuildMessage(){
		OwnReceiveHandler handler = new OwnReceiveHandler();
		Map<String,String> content = initContent();
		
		String companyId = "1";
		String opId = "1";
		String seq = "4";
		
		OwnResponse message = handler.buildMessage(
				companyId, opId,  seq, content);
		
		Assert.assertEquals("1", message.getCompanyId());
		Assert.assertEquals("1", message.getOpId());
		Assert.assertEquals("per_worker_info", message.getMessageType());
		Assert.assertEquals("4", message.getSeq());
		Assert.assertEquals("0005", message.getWorkId());
		Assert.assertEquals("0005", message.getName());
		Assert.assertEquals("3", message.getGroupAttribute());
		Assert.assertEquals("1,2", message.getGroupString());
		Assert.assertEquals("8622", message.getNumber());
		Assert.assertEquals("1", message.getLoginState());
		Assert.assertEquals("0", message.getMobileState());
		Assert.assertEquals("1", message.getState());
		Assert.assertEquals("1", message.getBindState());
		Assert.assertEquals("1", message.getCallState());
		Assert.assertEquals("0", message.getType());
		Assert.assertEquals("1234567", message.getMobileNumber());
		Assert.assertEquals("0", message.getWorkModel());
		
	}
	
	private Map<String,String> initContent(){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("WorkID", "0005");
		map.put("Name", "0005");
		map.put("GroupAttribute", "3");
		map.put("GroupString", "1,2");
		map.put("Number", "8622");
		map.put("LoginState", "1");
		map.put("MobileState", "0");
		map.put("State", "1");
		map.put("BindState", "1");
		map.put("CallState", "1");
		map.put("Type", "0");
		map.put("MobileNumber", "1234567");
		map.put("WorkModel", "0");
		
		return map;
	}
}
