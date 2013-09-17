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
	
	private final String _companyId;
	private final String _opId;
	private final String _messageType;
	private final String _seq;
	private final String _result;
	private final String _detail;
	
	public ResponseMessage(String companyId,String opId,String messageType,String seq){
		this(companyId,opId,messageType,seq,"");
	}
	
	public ResponseMessage(String companyId,String opId,String messageType,String seq,String result){
		_companyId = companyId;
		_opId = opId;
		_messageType = messageType;
		_seq = seq;
		_result = result;
		_detail = ResponseCode.codeInstance.getDetail(result);
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
	
	public String getResult(){
		return _result;
	}
	
	public String getDetail(){
		return _detail;
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
		builder.append(", _result=");
		builder.append(_result);
		builder.append(", _detail=");
		builder.append(_detail);
		builder.append("]");
		return builder.toString();
	}
}
