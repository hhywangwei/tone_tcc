package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.MobileNumberCancel;

import java.util.Map;

import com.tcc.cti.core.message.response.MobileNumberCancelResponse;
import com.tcc.cti.core.message.response.ResponseMessage;

/**
 * 接受取消移动座席设置返回信息
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class MobileNumberCancelReceiveHandler extends AbstractReceiveHandler{
	private static final String RESULT_PARAMETER = "result";
	
	@Override
	protected boolean isReceive(String msgType) {
		return MobileNumberCancel.isResponse(msgType);
	}

	@Override
	protected ResponseMessage buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		
        String result = content.get(RESULT_PARAMETER);
		
		return new MobileNumberCancelResponse(companyId,opId,seq,result);
	}

}
