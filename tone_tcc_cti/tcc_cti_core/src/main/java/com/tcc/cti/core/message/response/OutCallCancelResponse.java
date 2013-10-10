package com.tcc.cti.core.message.response;

import static com.tcc.cti.core.message.MessageType.OutCallCancel;

/**
 * 接收取消外呼对象
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallCancelResponse extends ResponseMessage{
	private final String _result;

	public OutCallCancelResponse(String companyId, String opId, String seq,String result) {
		super(companyId, opId, OutCallCancel.response(), seq);
		_result = result;
	}
	
	public String getResult(){
		return _result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OutCallCancelResponse [_result=");
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
