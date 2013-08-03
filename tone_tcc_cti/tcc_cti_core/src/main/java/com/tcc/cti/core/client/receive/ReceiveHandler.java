package com.tcc.cti.core.client.receive;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.message.pool.CtiMessagePool;

/**
 * 消息处理接口
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface ReceiveHandler {
	
	/**
	 * 接受消息处理
	 * 
	 * @param message 接受消息字符串
	 * @param pool 存放接受消息池{@link CtiMessagePool}
	 * @throws ClientException
	 */
	void receive(String message,CtiMessagePool pool)throws ClientException;
	
}
