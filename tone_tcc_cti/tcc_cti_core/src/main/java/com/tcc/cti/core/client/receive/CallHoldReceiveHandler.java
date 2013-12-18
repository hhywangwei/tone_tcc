package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.CallHold;

import java.util.Map;

import com.tcc.cti.core.message.response.CallHoldResponse;
import com.tcc.cti.core.message.response.ResponseMessage;

public class CallHoldReceiveHandler extends AbstractReceiveHandler {
	private static final String RESULT_PARAMETER = "result";

	@Override
	protected boolean isReceive(String msgType) {
		return CallHold.isResponse(msgType);
	}

	@Override
	protected ResponseMessage buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		
		String result = content.get(RESULT_PARAMETER);
		return new CallHoldResponse(companyId,opId,seq,result);
	}

}
