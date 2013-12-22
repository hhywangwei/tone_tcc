package com.tcc.cti.driver.message.request;

import java.util.List;

import com.tcc.cti.driver.message.RequestTimeoutException;
import com.tcc.cti.driver.message.event.RequestEvent;
import com.tcc.cti.driver.message.response.Response;

/**
 * 请求CTI服务器请求消息接口，是发起请求消息对象。
 * 
 * <pre>
 * 为了与http协议集成（请求回应），通过Requestable实现对象发起请求，通过{@link response}方法得到接收消息，
 * 如在指定时间为接收到消息则自动返回空消息
 * </pre>
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 *
 * @param <T> 服务返回消息对象类型
 */
public interface Requestable<T extends Response> {
	
	/**
	 * 注册请求消息事件对象{@link RequestEvent}
	 * 
	 * @param event 事件对象
	 */
	void regsiterEvent(RequestEvent event);
	
	/**
	 * 消息类型
	 * 
	 * @return
	 */
	String getMessageType();
	
	/**
	 * 通知发送消息
	 * 
	 * @param seq
	 */
	void notifySend(String seq);
	
	/**
	 * 通知发送错误消息
	 * 
	 * @param e 错误异常 
	 */
	void notifySendError(Throwable e);
	
	/**
	 * 设置接收对象
	 * 
	 * @param response 接收消息对象
	 */
	void receive(T response);
	
	/**
	 * 接收服务端返回对象，在10秒内未接收到服务消息，则直接返回。
	 * 
	 * @return 返回消息对象
	 * @throws InterruptedException
	 * @throws RequestTimeoutException 超时异常
	 */
	List<T> response()throws InterruptedException,RequestTimeoutException;
	
	/**
	 * 接收服务端返回对象，在指定的时间内{@code timeout}未接收到服务消息，则直接返回。
	 * 
	 * @param timeout
	 * @return
	 * @throws InterruptedException
	 * @throws RequestTimeoutException 超时异常
	 */
	List<T> response(int timeout)throws InterruptedException,RequestTimeoutException;
}
