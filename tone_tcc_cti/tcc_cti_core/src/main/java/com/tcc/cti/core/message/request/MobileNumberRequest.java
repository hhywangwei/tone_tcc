package com.tcc.cti.core.message.request;

import com.tcc.cti.core.message.MessageType;

/**
 * 请求移动座席设置对象
 * 
 * <pre>
 * number:移动电话
 * </pre>
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class MobileNumberRequest extends RequestMessage {
	private String number;
	
	public MobileNumberRequest() {
		super(MessageType.MobileNumber.request());
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "MobileNumberRequest [number=" + number + ", _messageType="
				+ _messageType + "]";
	}
}
