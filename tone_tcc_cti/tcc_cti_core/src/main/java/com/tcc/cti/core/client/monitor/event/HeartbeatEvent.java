package com.tcc.cti.core.client.monitor.event;

/**
 * 心跳发生事件
 * 
 * @author <a ref="hhywangwei@gmail.com">WangWei</a>
 */
public interface HeartbeatEvent{
	
	/**
	 * 发送成功
	 */
	void success();
	
	/**
	 * 发送失败
	 * 
	 * @param e
	 */
	void fail(Throwable e);
}