package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Record;

/**
 * 电话录入消息对象
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class RecordRequest extends RequestMessage{
	private String callLeg;
	
	public RecordRequest() {
		super(Record.request());
	}

	public String getCallLeg() {
		return callLeg;
	}

	public void setCallLeg(String callLeg) {
		this.callLeg = callLeg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RecordRequest [callLeg=");
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
