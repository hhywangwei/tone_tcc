package com.tcc.cti.core.message.send;


/**
 * 发送登录信息
 * 
 * <pre>
 * _opNumber:座席号
 * _password:用户密码使用（sh1）加密传输
 * _type:用户类型 
 * 
 * 详细参考{@link LoginSendHandler}
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class LoginSendMessage extends SendMessage{
	private String _opNumber;
	private String _password;
	private String _type;
	
	public String getOpNumber() {
		return _opNumber;
	}
	public void setOpNumber(String opNumber) {
		this._opNumber = opNumber;
	}
	public String getPassword() {
		return _password;
	}
	public void setPassword(String password) {
		this._password = password;
	}
	public String getType() {
		return _type;
	}
	public void setType(String type) {
		this._type = type;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Login [_compayId=");
		builder.append(_compayId);
		builder.append(", _opId=");
		builder.append(_opId);
		builder.append(", _opNumber=");
		builder.append(_opNumber);
		builder.append(", _password=");
		builder.append(_password);
		builder.append(", _type=");
		builder.append(_type);
		builder.append("]");
		return builder.toString();
	}
}
