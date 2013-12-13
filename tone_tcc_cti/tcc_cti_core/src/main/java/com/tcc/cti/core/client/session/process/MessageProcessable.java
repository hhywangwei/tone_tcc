package com.tcc.cti.core.client.session.process;

import com.tcc.cti.core.client.session.Sessionable;

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
