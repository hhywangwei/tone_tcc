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
	Call("get_call_info","add_call"),
	OutCall("outcall","outcall"),
	OutCallState("","outcallresp"),
	OutCallCancel("outcallcancel","outcallcancel"),
	Record("start_record","start_record");
	
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
