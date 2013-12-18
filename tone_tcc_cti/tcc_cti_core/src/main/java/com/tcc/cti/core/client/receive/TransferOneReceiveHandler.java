package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.TransferOne;

import java.util.Map;

import com.tcc.cti.core.message.response.ResponseMessage;
import com.tcc.cti.core.message.response.TransferOneResponse;

public class TransferOneReceiveHandler extends AbstractReceiveHandler {
	private static final String RESULT_PARAMETER = "result";
	
	@Override
	protected boolean isReceive(String msgType) {
		return TransferOne.isResponse(msgType);
	}

	@Override
	protected ResponseMessage buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		
		String result = content.get(RESULT_PARAMETER);
		return new TransferOneResponse(companyId,opId,seq,result);
	}

}
