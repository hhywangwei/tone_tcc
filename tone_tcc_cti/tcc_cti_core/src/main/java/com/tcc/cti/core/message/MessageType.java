package com.tcc.cti.core.message;

/**
 * 消息类型,根据cti服务消息类型定义
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public enum MessageType {
	
	Login("login"),SelfInfo("per_worker_info");
	
	private final String _type;
	
	private MessageType(String type){
		_type = type;
	}
	
	public String getType(){
		return this._type;
	}
}
