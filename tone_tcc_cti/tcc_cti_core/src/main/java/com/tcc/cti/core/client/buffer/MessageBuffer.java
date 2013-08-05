package com.tcc.cti.core.client.buffer;

/**
 * 消息缓存，从socket接收的消息转存到该缓存区等待读出处理
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface MessageBuffer {
	
	/**
	 * 得到下一条消息
	 * 
	 * @return
	 * @exception InterruptedException
	 */
	String getNext()throws InterruptedException;
	
	/**
	 * 添加网络接受消息到缓存中等待处理
	 * 
	 * @param bytes
	 * @exception InterruptedException
	 */
	void append(byte[] bytes)throws InterruptedException;

}
