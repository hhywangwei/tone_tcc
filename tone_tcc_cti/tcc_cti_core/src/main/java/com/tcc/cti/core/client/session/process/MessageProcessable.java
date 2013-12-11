package com.tcc.cti.core.client.session.process;

import java.util.List;

import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.message.pool.CtiMessagePool;

/**
 * 服务器消息处理接口
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public interface MessageProcessable {
	
	/**
	 * 推送接收消息进行处理
	 * 
	 * @param session {@link Sessionable}
	 * @param m 接收消息
	 * @throws InterruptedException
	 */
	void put(Sessionable session,String m)throws InterruptedException;
	
	/**
	 * 设置消息处理集合
	 * 
	 * @param handlers
	 */
	void setReceiveHandlers(List<ReceiveHandler> handlers);
	
	/**
	 * 设置消息池，处理后消息存放地方等待客户端接收
	 * 
	 * @param pool 消息池
	 */
	void setMessagePool(CtiMessagePool pool);
	
	/**
	 * 开始消息处理
	 */
	void start();
	
	/**
	 * 关闭消息处理
	 */
	void close();
	
	/**
	 * 判断消息处理开始
	 * 
	 * @return {@value true}开始消息处理
	 */
	boolean isStart();
}
