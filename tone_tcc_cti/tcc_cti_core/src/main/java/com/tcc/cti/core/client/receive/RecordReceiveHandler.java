package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Record;

import java.util.Map;

import com.tcc.cti.core.message.response.RecordResponse;
import com.tcc.cti.core.message.response.ResponseMessage;

/**
 * 接受录入消息对象
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class RecordReceiveHandler extends AbstractReceiveHandler{
	private static final String RESULT_PARAMETER = "result";
	
	@Override
	protected boolean isReceive(String msgType) {
		return Record.isResponse(msgType);
	}

	@Override
	protected ResponseMessage buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
	
		String r = content.get(RESULT_PARAMETER);
		return new RecordResponse(companyId,opId,seq,r);
	}

}
