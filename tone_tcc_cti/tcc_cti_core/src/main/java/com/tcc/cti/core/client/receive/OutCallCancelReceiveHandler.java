package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.OutCallCancel;

import java.util.Map;

import com.tcc.cti.core.message.response.OutCallCancelResponse;
import com.tcc.cti.core.message.response.ResponseMessage;

/**
 * 接收取消外呼状态
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallCancelReceiveHandler extends AbstractReceiveHandler{
	private static final String RESULT_PARAMETER = "result";
	
	@Override
	protected boolean isReceive(String msgType) {
		return OutCallCancel.isResponse(msgType);
	}

	@Override
	protected ResponseMessage buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		
		String result = content.get(RESULT_PARAMETER);
		
		return new OutCallCancelResponse(companyId,opId,seq,result);
	}

}
