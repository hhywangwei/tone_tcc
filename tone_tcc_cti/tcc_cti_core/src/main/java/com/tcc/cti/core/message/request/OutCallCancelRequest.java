package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.OutCallCancel;

/**
 * 取消外呼对象
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 *
 */
public class OutCallCancelRequest extends RequestMessage{
	
	private String callLeg;

	public OutCallCancelRequest() {
		super(OutCallCancel.request());
	}
	
	public void setCallLeg(String callLeg){
		this.callLeg = callLeg;
	}
	
	public String getCallLeg(){
		return this.callLeg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OutCallCancelRequest [callLeg=");
		builder.append(callLeg);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append(", _compayId=");
		builder.append(_compayId);
		builder.append(", _opId=");
		builder.append(_opId);
		builder.append("]");
		return builder.toString();
	}
}
