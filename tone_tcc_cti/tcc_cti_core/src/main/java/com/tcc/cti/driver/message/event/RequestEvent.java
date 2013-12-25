package com.tcc.cti.driver.message.event;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;

/**
 * 请求消息事件，监控消息发送和接收消息完成回调处理
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public interface RequestEvent {
	
	/**
	 * 发送命令前事件触发回调函数
	 * 
	 * @param opertor 操作用户
	 * @param seq     消息序列
	 * @param request 请求对象
	 */
	void beforeSend(Operator opertor,String seq,Requestable<? extends Response> request);
	
	/**
	 * 接收服务端消息完成回调函数
	 * 
	 * @param operator    操作用户
	 * @param seq         消息编号
	 * @param messageType 消息类型
	 */
	void finishReceive(Operator operator,String seq,String messageType);
}
