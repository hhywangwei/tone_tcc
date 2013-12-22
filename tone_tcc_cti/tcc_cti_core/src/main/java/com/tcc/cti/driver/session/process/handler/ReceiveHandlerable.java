package com.tcc.cti.driver.session.process.handler;

import com.tcc.cti.driver.session.Sessionable;
import com.tcc.cti.driver.session.process.Requestsable;
import com.tcc.cti.driver.session.process.handler.receive.ParseMessageException;

/**
 * 接收消息处理接口
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public interface ReceiveHandlerable {
	
	/**
	 * 接受消息处理
	 * 
	 * @param requests 请求集合
	 * @param session 操作频道{@link Sessionable}
	 * @param message 接受消息字符串
	 * 
	 * @throws ParseMessageException
	 */
	void receive(Requestsable requests,	Sessionable session, String message)throws ParseMessageException;
}
