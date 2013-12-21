package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.OutCall;

import com.tcc.cti.core.message.response.Response;

/**
 * 外呼请求消息对象
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallRequest extends BaseRequest<Response>{
	
	private String _opNumber;
	private String _phone;

	public OutCallRequest() {
		super(OutCall.request());
	}
	
	public OutCallRequest(int timeout){
		super(OutCall.request(),timeout);
	}

	public String getPhone(){
		return _phone;
	}
	
	public void setPhone(String phone){
		_phone = phone;
	}
	
	public String getOpNumber(){
		return _opNumber;
	}
	
	public void setOpNumber(String opNumber){
		_opNumber = opNumber;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OutCallRequest [_opNumber=");
		builder.append(_opNumber);
		builder.append(", _phone=");
		builder.append(_phone);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
}
