package com.tcc.cti.driver.session.process.handler.receive;

import static com.tcc.cti.driver.message.MessageType.CallHelp;
import static com.tcc.cti.driver.message.MessageType.CallHold;
import static com.tcc.cti.driver.message.MessageType.Logout;
import static com.tcc.cti.driver.message.MessageType.MobileNumber;
import static com.tcc.cti.driver.message.MessageType.MobileNumberCancel;
import static com.tcc.cti.driver.message.MessageType.OutCall;
import static com.tcc.cti.driver.message.MessageType.OutCallCancel;
import static com.tcc.cti.driver.message.MessageType.Rest;
import static com.tcc.cti.driver.message.MessageType.Resume;
import static com.tcc.cti.driver.message.MessageType.Silence;
import static com.tcc.cti.driver.message.MessageType.Status;
import static com.tcc.cti.driver.message.MessageType.TransferGroup;
import static com.tcc.cti.driver.message.MessageType.TransferOne;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link CommonReceiveHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class CommonReceiveHandlerTest {
	
	@Test
	public void testIsReceive(){
		CommonReceiveHandler handler = new CommonReceiveHandler();
		
		Assert.assertFalse(handler.isReceive(null));
		Assert.assertFalse(handler.isReceive("not"));
		Assert.assertTrue(handler.isReceive(CallHelp.response()));
		Assert.assertTrue(handler.isReceive(CallHold.response()));
		Assert.assertTrue(handler.isReceive(Logout.response()));
		Assert.assertTrue(handler.isReceive(MobileNumberCancel.response()));
		Assert.assertTrue(handler.isReceive(MobileNumber.response()));
		Assert.assertTrue(handler.isReceive(OutCallCancel.response()));
		Assert.assertTrue(handler.isReceive(OutCall.response()));
		Assert.assertTrue(handler.isReceive(Rest.response()));
		Assert.assertTrue(handler.isReceive(Resume.response()));
		Assert.assertTrue(handler.isReceive(Silence.response()));
		Assert.assertTrue(handler.isReceive(Status.response()));
		Assert.assertTrue(handler.isReceive(TransferGroup.response()));
		Assert.assertTrue(handler.isReceive(TransferOne.response()));
	}
}
