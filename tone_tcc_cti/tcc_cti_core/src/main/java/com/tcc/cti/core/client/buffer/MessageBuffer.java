package com.tcc.cti.core.client.buffer;

/**
 * 消息缓存，从socket接收的消息转存到该缓存区等待读出处理
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface MessageBuffer {
	
	/**
	 * 得到服务端消息,当消息池为空时{@code wait}等待新的消息进入
	 * 
	 * @return 服务端消息
	 * @throws InterruptedException 
	 */
	String next()throws InterruptedException;
	
	/**
	 * 添加接受服务端消息到缓存中等待处理，如果缓存已满则直接清除老缓存，再添加新消息。
	 * 
	 * <pre>
	 * 注意：不能使用{@code wait()}阻塞，这会终止所有接收线程运行。
	 * </pre>
	 * 
	 * @param bytes 写入消息到缓冲区
	 * @return true 添加成功
	 */
	boolean append(byte[] bytes);

}
