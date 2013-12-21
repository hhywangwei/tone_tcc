package com.tcc.cti.core.client.receive;

import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.client.session.process.Requestsable;

/**
 * 接收消息处理接口
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public interface ReceiveHandler {
	
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
