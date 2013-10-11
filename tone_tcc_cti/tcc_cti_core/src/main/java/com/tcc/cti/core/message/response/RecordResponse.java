package com.tcc.cti.core.message.response;

import static com.tcc.cti.core.message.MessageType.Record;

/**
 * 接收录入消息对象
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class RecordResponse extends ResponseMessage{
	private final String _result;

	public RecordResponse(String companyId, String opId, String seq, String result) {
		super(companyId, opId, Record.response(), seq);
		_result = result;
	}
	
	public String getResult(){
		return _result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RecordResponse [_result=");
		builder.append(_result);
		builder.append(", _companyId=");
		builder.append(_companyId);
		builder.append(", _opId=");
		builder.append(_opId);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append(", _seq=");
		builder.append(_seq);
		builder.append("]");
		return builder.toString();
	}

}
