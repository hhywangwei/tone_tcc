package com.tcc.cti.core.message;

/**
 * 消息类型,根据cti服务消息类型定义
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public enum MessageType {
	
	Login("login","login"),
	Heartbeat("hb","hb"),
	Own("per_worker_info","per_worker_info"),
	GroupMember("worker_number_info","add_op"),
	Group("group_info","add_group"),
	Monitor("monitor_info","add_monitor"),
	Call("get_call_info","add_call"),
	CloseCall("","delete_call"),
	OutCall("outcall","outcall"),
	OutCallState("","outcallresp"),
	OutCallCancel("outcallcancel","outcallcancel"),
	Record("start_record","start_record"),
	MobileNumber("set_mobile_number","set_mobile_number"),
	MobileNumberCancel("cancel_mobile_number","cancel_mobile_number"),
	Password("modify_password","modify_password"),
	Rest("leave","leave"),
	Resume("come_back","come_back"),
	Silence("silence","silence"),
	CallHold("call_hold","call_hold"),
	CallHelp("transfer","transfer"),
	TransferGroup("transfer_to_group","transfer_to_group"),
	TransferOne("transfer_to_one","transfer_to_one"),
	Status("set_status","set_status"),
	Logout("logout","logout");
	
	private final String _request;
	private final String _response;
	
	private MessageType(String request,String response){
		_request = request;
		_response = response;
	}
	
	public String request(){
		return _request;
	}
	
	public boolean isRequest(String request){
		return request != null && _request.equals(request);
	}
	
	public String response(){
		return _response;
	}
	
	public boolean isResponse(String response){
		return response != null && _response.equals(response);
	}
}
