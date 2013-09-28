package com.tcc.cti.core.message.request;

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
public class RequestMessage {

	protected final String _messageType;
	protected String _compayId;
	protected String _opId;
	
	public RequestMessage(String messageType){
		_messageType = messageType;
	}
	
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
	public String getMessageType(){
		return _messageType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestMessage [_messageType=");
		builder.append(_messageType);
		builder.append(", _compayId=");
		builder.append(_compayId);
		builder.append(", _opId=");
		builder.append(_opId);
		builder.append("]");
		return builder.toString();
	}
}
