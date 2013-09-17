package com.tcc.cti.core.message.response;

/**
 * 接收消息
 * 
 * <pre>
 * _companyId:公司编号
 * _opId:操作员编号
 * _messageType:消息类型
 * _seq:序号
 * _result:返回结果
 * _detail:返回结果描述
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 *
 */
public class ResponseMessage {
	
	protected final String _companyId;
	protected final String _opId;
	protected final String _messageType;
	protected final String _seq;
	
	public ResponseMessage(String companyId,String opId,String messageType,String seq){
		_companyId = companyId;
		_opId = opId;
		_messageType = messageType;
		_seq = seq;
	}
	
	public String getCompanyId(){
		return _companyId;
	}
	
	public String getOpId(){
		return _opId;
	}
	
	public String getMessageType(){
		return _messageType;
	}
	
	public String getSeq(){
		return _seq;
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReceiveMessage [_companyId=");
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
