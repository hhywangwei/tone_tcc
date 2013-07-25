package org.tcc_cti_core.model;

/**
 * 登录用户信息对象
 * 
 * <pre>
 * compayId:公司编号
 * opId:用户编号与公司编号组合决定员工唯一性
 * opNumber:座席号
 * password:用户密码使用（sh1）加密传输
 * type:用户类型 
 * 
 * 详细参考{@link LoginMessage}
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class Login {
	private String compayId;
	private String opId;
	private String opNumber;
	private String password;
	private String type;
	
	public String getCompayId() {
		return compayId;
	}
	public void setCompayId(String compayId) {
		this.compayId = compayId;
	}
	public String getOpId() {
		return opId;
	}
	public void setOpId(String opId) {
		this.opId = opId;
	}
	public String getOpNumber() {
		return opNumber;
	}
	public void setOpNumber(String opNumber) {
		this.opNumber = opNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((compayId == null) ? 0 : compayId.hashCode());
		result = prime * result + ((opId == null) ? 0 : opId.hashCode());
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
		Login other = (Login) obj;
		if (compayId == null) {
			if (other.compayId != null)
				return false;
		} else if (!compayId.equals(other.compayId))
			return false;
		if (opId == null) {
			if (other.opId != null)
				return false;
		} else if (!opId.equals(other.opId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Login [compayId=");
		builder.append(compayId);
		builder.append(", opId=");
		builder.append(opId);
		builder.append(", opNumber=");
		builder.append(opNumber);
		builder.append(", password=");
		builder.append(password);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}
}
