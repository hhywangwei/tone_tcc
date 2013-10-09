package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.OutCallState;

import java.util.Map;

import com.tcc.cti.core.message.response.OutCallStateResponse;
import com.tcc.cti.core.message.response.ResponseMessage;

public class OutCallStateReceiveHandler extends AbstractReceiveHandler{
	private static final String STATE_PARAMETER = "State";
	private static final String CALL_LEG_PARAMETER = "CallLeg";
	
	@Override
	protected boolean isReceive(String msgType) {
		return OutCallState.response().equals(msgType);
	}

	@Override
	protected ResponseMessage buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		String callLeg = content.get(CALL_LEG_PARAMETER);
		String state = content.get(STATE_PARAMETER);
		return new OutCallStateResponse(companyId,opId,seq,callLeg,state);
	}

}
