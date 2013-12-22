package com.tcc.cti.core.client.session.process.handler.receive;

import static com.tcc.cti.core.message.MessageType.CallHelp;
import static com.tcc.cti.core.message.MessageType.CallHold;
import static com.tcc.cti.core.message.MessageType.Logout;
import static com.tcc.cti.core.message.MessageType.MobileNumberCancel;
import static com.tcc.cti.core.message.MessageType.MobileNumber;
import static com.tcc.cti.core.message.MessageType.OutCallCancel;
import static com.tcc.cti.core.message.MessageType.OutCall;
import static com.tcc.cti.core.message.MessageType.Rest;
import static com.tcc.cti.core.message.MessageType.Resume;
import static com.tcc.cti.core.message.MessageType.Silence;
import static com.tcc.cti.core.message.MessageType.Status;
import static com.tcc.cti.core.message.MessageType.TransferGroup;
import static com.tcc.cti.core.message.MessageType.TransferOne;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.client.session.process.handler.receive.CommonReceiveHandler;

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
