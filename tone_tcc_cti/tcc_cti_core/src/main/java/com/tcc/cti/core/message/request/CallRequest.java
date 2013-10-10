package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Call;

/**
 * 发送获取呼叫信息对象
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class CallRequest extends RequestMessage{

	public CallRequest() {
		super(Call.request());
	}

}
