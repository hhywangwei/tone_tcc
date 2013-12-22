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

public class CommonReceiveHandler extends AbstractReceiveHandler{

	@Override
	protected boolean isReceive(String msgType) {
		return CallHelp.isResponse(msgType) ||
				CallHold.isResponse(msgType) ||
				Logout.isResponse(msgType) ||
				MobileNumberCancel.isResponse(msgType) ||
				MobileNumber.isResponse(msgType) ||
				OutCallCancel.isResponse(msgType) ||
				OutCall.isResponse(msgType) ||
				Rest.isResponse(msgType) ||
				Resume.isResponse(msgType) ||
				Silence.isResponse(msgType) ||
				Status.isResponse(msgType) ||
				TransferGroup.isResponse(msgType) ||
				TransferOne.isResponse(msgType);
	}
}
