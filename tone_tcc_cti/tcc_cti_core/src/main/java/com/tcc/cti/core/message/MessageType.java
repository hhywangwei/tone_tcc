package com.tcc.cti.core.message;

/**
 * 消息类型,根据cti服务消息类型定义
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public enum MessageType {
	
	Login("login","login"),
	SelfInfo("per_worker_info","per_worker_info"),
	ObtainMember("worker_number_info","add_op");
	
	private final String _requestType;
	private final String _reponseType;
	
	private MessageType(String requestType,String reponseType){
		_requestType = requestType;
		_reponseType = reponseType;
	}
	
	public String requestType(){
		return _requestType;
	}
	
	public String responseType(){
		return _reponseType;
	}
}
