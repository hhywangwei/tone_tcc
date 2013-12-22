package com.tcc.cti.driver.session;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;

/**
 * 建立服务端用户都会产对应的Session对象，保留与服务连接的Channel并接收服务端数据。
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface Sessionable {
	
	/**
	 * {@link Session}状态
	 * 
	 * <pre>New:初始状态。</pre>
	 * <pre>Active:激活状态，已经连接CTI服务器，但是用户还未登陆成功。</pre>
	 * <pre>Service:开始服务状态，已经连接CTI服务器，用户已经登陆成功。</pre>
	 * <pre>Timeout:连接超时状态，在规定的时间内，未接收到服务端心跳。</pre>
	 * <pre>Close:关闭状态。</pre>
	 */
	public enum Status {
		New,
		Active,
		Service,
		Timeout,
		Close;}

	/**
	 * Session开始连接服务器
	 * 
	 * @throws IOException
	 */
	void start()throws IOException;
	
	/**
	 * 关闭Session释放资源
	 * 
	 * @throws IOException
	 */
	void close()throws IOException;
	
	/**
	 * 登录设置
	 * 
	 * @param success true:登录成功
	 */
	void login(boolean success);
	
	/**
	 * 接收到心跳后调用该方法，保存Session处于不离线状态
	 */
	void heartbeatTouch();
	
	/**
	 * 添加服务端接收消息到Session中处理
	 * 
	 * @param bytes 接收的消息
	 */
	void append(byte[] bytes);
	
	/**
	 * 发送消息
	 * 
	 * @param message 发送消息对象
	 * @throws IOException
	 */
	void send(Requestable<? extends Response> message)throws IOException;
	
	/**
	 * 得到操作员key对象
	 * 
	 * @return
	 */
	Operator getOperatorKey();
	
	/**
	 * 连接服务端{@link SocketChannel}
	 * 
	 * @return
	 */
	SocketChannel getSocketChannel();
	
	/**
	 * 得到Session当前状态{@link Status}
	 * 
	 * @return
	 */
	Status getStatus();
	
	/**
	 * 是初始状态，还未连接到CTI服务器
	 * 
	 * @return true 初始状态
	 */
	boolean isNew();
	
	/**
	 * 是激活状态，已经连接CTI服务器，但是用户还未登陆成功
	 * 
	 * @return true 激活状态
	 */
	boolean isActive();
	
	/**
	 * 是开始服务状态，已经连接CTI服务器，用户已经登陆成功
	 * 
	 * @return true 开始服务状态
	 */
	boolean isService();
	
	/**
	 * 是关闭状态
	 * 
	 * @return true 关闭状态
	 */
	boolean isClose();
	
	/**
	 * 是连接超时状态，在规定的时间内，未接收到服务端心跳
	 * 
	 * @return
	 */
	boolean isTimeout();
}
