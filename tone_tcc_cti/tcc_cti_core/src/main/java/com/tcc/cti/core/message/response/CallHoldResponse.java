package com.tcc.cti.core.message.response;

import static com.tcc.cti.core.message.MessageType.CallHold;;

/**
 * 接收服务端保存通话
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class CallHoldResponse extends SilenceResponse {

	public CallHoldResponse(String companyId, String opId, String seq, String result) {
		
		super(companyId, opId, CallHold.response(), seq, result);
	}

}
