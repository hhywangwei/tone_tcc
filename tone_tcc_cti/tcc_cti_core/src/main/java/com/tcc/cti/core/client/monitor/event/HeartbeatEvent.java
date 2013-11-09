package com.tcc.cti.core.client.monitor.event;

import java.nio.ByteBuffer;

/**
 * 心跳发生事件
 * 
 * @author <a ref="hhywangwei@gmail.com">WangWei</a>
 */
public interface HeartbeatEvent{
	
	/**
	 * 发送成功
	 * 
	 * @param buffer
	 */
	void success(ByteBuffer buffer);
	
	/**
	 * 发送失败
	 * 
	 * @param buffer
	 * @param e
	 */
	void fail(ByteBuffer buffer,Throwable e);
}