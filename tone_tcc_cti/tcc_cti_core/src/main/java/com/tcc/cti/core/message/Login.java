package com.tcc.cti.core.message;

/**
 * 登录用户信息对象
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
public class Login extends CtiMessage{
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_compayId == null) ? 0 : _compayId.hashCode());
		result = prime * result + ((_opId == null) ? 0 : _opId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CtiMessage other = (CtiMessage) obj;
		if (_compayId == null) {
			if (other._compayId != null)
				return false;
		} else if (!_compayId.equals(other._compayId))
			return false;
		if (_opId == null) {
			if (other._opId != null)
				return false;
		} else if (!_opId.equals(other._opId))
			return false;
		return true;
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
