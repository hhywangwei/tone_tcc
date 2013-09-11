package com.tcc.cti.core.message.receive;

/**
 * 接受消息
 * 
 * <per>
 * _companyId:公司编号
 * _opId:操作员编号
 * _type:消息类型
 * _message:消息内容
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 *
 */
public class ReceiveMessage {
	
	private final String _companyId;
	private final String _opId;
	private final String _type;
	private final String _result;
	private final String _detail;
	
	public ReceiveMessage(String companyId,String opId,String type){
		this(companyId,opId,type,"");
	}
	
	public ReceiveMessage(String companyId,String opId,String type,String result){
		_companyId = companyId;
		_opId = opId;
		_type = type;
		_result = result;
		_detail = ReceiveCode.codeInstance.getDetail(result);
	}
	
	public String getCompanyId(){
		return _companyId;
	}
	
	public String getOpId(){
		return _opId;
	}
	
	public String getType(){
		return _type;
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
		builder.append(", _type=");
		builder.append(_type);
		builder.append(", _result=");
		builder.append(_result);
		builder.append(", _detail=");
		builder.append(_detail);
		builder.append("]");
		return builder.toString();
	}
}
