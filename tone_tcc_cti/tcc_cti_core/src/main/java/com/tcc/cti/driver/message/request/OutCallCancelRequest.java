package com.tcc.cti.driver.message.request;

import static com.tcc.cti.driver.message.MessageType.OutCallCancel;

import com.tcc.cti.driver.message.response.Response;

/**
 * 取消外呼对象
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 *
 */
public class OutCallCancelRequest extends PhoneStatusRequest<Response>{
	private String callLeg;

	public OutCallCancelRequest() {
		super(OutCallCancel.request());
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OutCallCancelRequest [callLeg=");
		builder.append(callLeg);
		builder.append(", _callLeg=");
		builder.append(_callLeg);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
}
