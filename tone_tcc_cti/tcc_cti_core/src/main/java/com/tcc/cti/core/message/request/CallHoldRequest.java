package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.CallHold;

/**
 * 保存通话请求对象
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class CallHoldRequest extends SilenceRequest {

	public CallHoldRequest(){
		super(CallHold.request());
	}
}
