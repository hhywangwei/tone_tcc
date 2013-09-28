package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Own;

/**
 * 获得坐席消息对象
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>S
 */
public class OwnRequest extends RequestMessage{

	public OwnRequest() {
		super(Own.response());
	}
}
