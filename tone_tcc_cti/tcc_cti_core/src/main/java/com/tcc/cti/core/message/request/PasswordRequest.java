package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Password;

import com.tcc.cti.core.common.PasswordUtils;

/**
 * 请求修改密码对象
 * 
 * <pre>
 * _password:修该的密码
 * </pre>
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class PasswordRequest extends RequestMessage{
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
		return "PasswordRequest [_password=" + _password + ", _messageType="
				+ _messageType + "]";
	}
}
