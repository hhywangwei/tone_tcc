package com.tcc.cti.core.message.response;

import static com.tcc.cti.core.message.MessageType.Login;

public class LoginResponse extends ResponseMessage{

	private final String _result;
	private final String _detail;
	
	public LoginResponse(String companyId, String opId,	String seq,String result) {
		super(companyId, opId, Login.response(), seq);
		_result = result;
		_detail = ResponseCode.codeInstance.getDetail(result);
	}
	
	public String getResult(){
		return _result;
	}
	
	public String getDetail(){
		return _detail;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginResponse [_result=");
		builder.append(_result);
		builder.append(", _detail=");
		builder.append(_detail);
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
