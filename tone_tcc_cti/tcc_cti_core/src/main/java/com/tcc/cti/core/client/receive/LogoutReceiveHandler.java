package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Logout;

import java.util.Map;

import com.tcc.cti.core.message.response.LogoutResponse;
import com.tcc.cti.core.message.response.ResponseMessage;

public class LogoutReceiveHandler extends AbstractReceiveHandler {
	private static final String RESULT_PARAMETER = "result";
	
	@Override
	protected boolean isReceive(String msgType) {
		return Logout.isResponse(msgType);
	}

	@Override
	protected ResponseMessage buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		
		String result = content.get(RESULT_PARAMETER);
		return new LogoutResponse(companyId,opId,seq,result);
	}

}
