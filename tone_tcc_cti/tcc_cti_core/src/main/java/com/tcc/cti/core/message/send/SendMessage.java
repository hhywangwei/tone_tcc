package com.tcc.cti.core.message.send;

/**
 * 发送消息
 * 
 * <pre>
 * _compayId:公司编号
 * _opId:用户编号与公司编号组合决定员工唯一性
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 *
 */
public abstract class SendMessage {

	protected String _compayId;
	protected String _opId;
	
	public String getCompayId() {
		return _compayId;
	}
	public void setCompayId(String compayId) {
		this._compayId = compayId;
	}
	public String getOpId() {
		return _opId;
	}
	public void setOpId(String opId) {
		this._opId = opId;
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
		SendMessage other = (SendMessage) obj;
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
}
