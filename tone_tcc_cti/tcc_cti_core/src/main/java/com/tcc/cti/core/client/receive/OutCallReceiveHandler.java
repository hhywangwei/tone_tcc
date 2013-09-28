package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.OutCall;

import java.util.Map;

import com.tcc.cti.core.message.response.OutCallResponse;
import com.tcc.cti.core.message.response.ResponseMessage;

/**
 * 服务器收到外呼信息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallReceiveHandler extends AbstractReceiveHandler{
	private static final String RESULT_PARAMETER = "result";
	
	@Override
	protected boolean isReceive(String msgType) {
		return OutCall.response().equals(msgType);
	}

	@Override
	protected ResponseMessage buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		
		String result = content.get(RESULT_PARAMETER);
		
		return new OutCallResponse(companyId,opId,seq,result);
	}

}
