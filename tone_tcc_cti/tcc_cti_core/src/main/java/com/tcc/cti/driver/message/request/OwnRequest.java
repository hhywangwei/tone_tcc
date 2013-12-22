package com.tcc.cti.driver.message.request;

import static com.tcc.cti.driver.message.MessageType.Own;

import com.tcc.cti.driver.message.response.OwnResponse;

/**
 * 获得坐席消息对象
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>S
 */
public class OwnRequest extends BaseRequest<OwnResponse>{

	public OwnRequest() {
		super(Own.response());
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OwnRequest [_messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
	
}
