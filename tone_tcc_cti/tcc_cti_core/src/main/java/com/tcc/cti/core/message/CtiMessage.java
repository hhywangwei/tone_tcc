package com.tcc.cti.core.message;

/**
 * <pre>
 * _compayId:公司编号
 * _opId:用户编号与公司编号组合决定员工唯一性
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 *
 */
public abstract class CtiMessage {

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

}
