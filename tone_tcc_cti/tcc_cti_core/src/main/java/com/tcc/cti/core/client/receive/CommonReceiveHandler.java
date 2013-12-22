package com.tcc.cti.core.client.receive;

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
