package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.SelfInfo;

/**
 * 获得坐席消息对象
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>S
 */
public class SelfInfoRequest extends RequestMessage{

	public SelfInfoRequest() {
		super(SelfInfo.responseType());
	}
}
