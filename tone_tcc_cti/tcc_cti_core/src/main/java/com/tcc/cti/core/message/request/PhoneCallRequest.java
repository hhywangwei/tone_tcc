package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.PhoneCall;

/**
 * 发送获取呼叫信息对象
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class PhoneCallRequest extends RequestMessage{

	public PhoneCallRequest() {
		super(PhoneCall.request());
	}

}
