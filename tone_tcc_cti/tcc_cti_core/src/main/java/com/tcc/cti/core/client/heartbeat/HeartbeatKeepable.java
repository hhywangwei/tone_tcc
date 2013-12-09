package com.tcc.cti.core.client.heartbeat;


/**
 * 检测与CTI服务器的tcp连接的有效性,客户端每20秒发出该消息，服务器端收到则答复。
 * 任一方发现超过65秒未收到对方的任何消息，则认为socket有问题，必须关闭。
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 *
 */
public interface HeartbeatKeepable {

	/**
	 * 开始心跳
	 */
	void start();
	
	/**
	 * 停止心跳
	 */
	void shutdown();
	
	/**
	 * 开始心跳
	 * 
	 * @return true 已经开始心跳
	 */
	boolean isStart();
	
	/**
	 * 停止心跳
	 * 
	 * @return true 已经停止心跳
	 */
	boolean isShutdown();
	
}
