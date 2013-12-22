package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.MobileNumber;

import com.tcc.cti.core.message.response.Response;

/**
 * 请求移动座席设置对象
 * 
 * <pre>
 * number:移动电话
 * </pre>
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class MobileNumberRequest extends BaseRequest<Response> {
	private String number;
	
	public MobileNumberRequest() {
		super(MobileNumber.request());
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MobileNumberRequest [number=");
		builder.append(number);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
}
