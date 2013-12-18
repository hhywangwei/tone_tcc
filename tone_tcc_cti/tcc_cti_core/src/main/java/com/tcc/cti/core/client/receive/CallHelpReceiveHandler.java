package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.CallHelp;

import java.util.Map;

import com.tcc.cti.core.message.response.CallHelpResponse;
import com.tcc.cti.core.message.response.ResponseMessage;

public class CallHelpReceiveHandler extends AbstractReceiveHandler {
	private static final String RESULT_PARAMETER = "result";
	
	@Override
	protected boolean isReceive(String msgType) {
		return CallHelp.isResponse(msgType);
	}

	@Override
	protected ResponseMessage buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		String result = content.get(RESULT_PARAMETER);
		
		return new CallHelpResponse(companyId,opId,seq,result);
	}

}
