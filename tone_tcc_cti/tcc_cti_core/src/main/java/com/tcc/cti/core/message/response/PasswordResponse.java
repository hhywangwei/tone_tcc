package com.tcc.cti.core.message.response;

import static com.tcc.cti.core.message.MessageType.Password;

/**
 * 接收修改密码信息
 * 
 * <pre>
 * _result:修改密码
 * </pre>
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class PasswordResponse extends ResponseMessage {
	private final String _result;
	
	public PasswordResponse(String companyId, String opId, 
			String seq,String result) {
		
		super(companyId, opId, Password.response(), seq);
		_result = result;
	}

	public String getResult() {
		return _result;
	}

	@Override
	public String toString() {
		return "PasswordResponse [_result=" + _result + ", _companyId="
				+ _companyId + ", _opId=" + _opId + ", _messageType="
				+ _messageType + ", _seq=" + _seq + "]";
	}
}
