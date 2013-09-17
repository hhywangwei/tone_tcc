package com.tcc.cti.core.client.receive;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.client.OperatorChannel;
import com.tcc.cti.core.message.pool.CtiMessagePool;

/**
 * 接收消息处理接口
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface ReceiveHandler {
	
	/**
	 * 接受消息处理
	 * @param pool 存放接受消息池{@link CtiMessagePool}
	 * @param channel 操作频道{@link OperatorChannel}
	 * @param message 接受消息字符串
	 * 
	 * @throws ClientException
	 */
	void receive(CtiMessagePool pool,OperatorChannel channel, String message)throws ClientException;
	
}
