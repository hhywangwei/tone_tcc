package com.tcc.cti.core.message.response;

/**
 * 接收呼叫帮助对象
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class CallHelpResponse extends SilenceResponse {

	public CallHelpResponse(String companyId, String opId, String seq,
			String result) {
		super(companyId, opId, seq, result);
	}

}
