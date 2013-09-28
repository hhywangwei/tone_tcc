package com.tcc.cti.core.message;

/**
 * 消息类型,根据cti服务消息类型定义
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public enum MessageType {
	
	Login("login","login"),
	Own("per_worker_info","per_worker_info"),
	GroupMember("worker_number_info","add_op"),
	Group("group_info","add_group"),
	Monitor("monitor_info","add_monitor"),
	PhoneCall("get_call_info","add_call"),
	OutCall("outcall","outcall");
	
	private final String _request;
	private final String _reponse;
	
	private MessageType(String request,String reponse){
		_request = request;
		_reponse = reponse;
	}
	
	public String request(){
		return _request;
	}
	
	public String response(){
		return _reponse;
	}
}
