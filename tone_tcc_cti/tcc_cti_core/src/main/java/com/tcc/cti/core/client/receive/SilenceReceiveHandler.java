package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Silence;

import java.util.Map;

import com.tcc.cti.core.message.response.ResponseMessage;
import com.tcc.cti.core.message.response.SilenceResponse;

/**
 * 接收服务端静消息处理
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class SilenceReceiveHandler extends AbstractReceiveHandler{
	private static final String RESULT_PARAMETER = "result";

	@Override
	protected boolean isReceive(String msgType) {
		return Silence.isResponse(msgType);
	}

	@Override
	protected ResponseMessage buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		
		String result = content.get(RESULT_PARAMETER);
		return new SilenceResponse(companyId,opId,seq,result);
	}
}
