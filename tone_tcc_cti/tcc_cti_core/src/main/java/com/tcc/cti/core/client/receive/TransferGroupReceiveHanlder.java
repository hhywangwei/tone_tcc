package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.TransferGroup;

import java.util.Map;

import com.tcc.cti.core.message.response.ResponseMessage;
import com.tcc.cti.core.message.response.TransferGroupResponse;

public class TransferGroupReceiveHanlder extends AbstractReceiveHandler{
	private static final String RESULT_PARAMETER = "result";
	
	@Override
	protected boolean isReceive(String msgType) {
		return TransferGroup.isResponse(msgType);
	}

	@Override
	protected ResponseMessage buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		
		String result = content.get(RESULT_PARAMETER);
		return new TransferGroupResponse(companyId,opId,seq,result);
	}

}
