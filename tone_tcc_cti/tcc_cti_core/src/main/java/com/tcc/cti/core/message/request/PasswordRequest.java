package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Password;

import com.tcc.cti.core.common.PasswordUtils;
import com.tcc.cti.core.message.response.Response;

/**
 * 请求修改密码对象
 * 
 * <pre>
 * _password:修该的密码
 * </pre>
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class PasswordRequest extends BaseRequest<Response>{
	private String _password;

	public PasswordRequest() {
		super(Password.request());
	}
	
	public String getPassword() {
		return _password;
	}

	public void setPassword(String password) {
		_password =PasswordUtils.encodeMD5(password);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PasswordRequest [_password=");
		builder.append(_password);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
}
