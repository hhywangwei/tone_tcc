package com.tcc.cti.core.client.session;

import java.io.IOException;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 建立服务端用户都会产对应的Session对象，保留与服务连接的Channel并接收服务端数据。
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface Sessionable {
	
	/**
	 * {@link Session}状态
	 * 
	 * <pre>None:初始状态</pre>
	 * <pre>Connecting:开始连接</pre>
	 * <pre>ConnectError:连接错误</pre>
	 * <pre>Connected:连接成功</pre>
	 * <pre>Login:登录成功</pre>
	 * <pre>LoginError:登录错误</pre>
	 * <pre>Close:关闭</pre>
	 */
	public enum Status {
		None,
		Connecting,
		ConnectError,
		Connected,
		Login,
		LoginError,
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
	 * 是否有效，连接上服务器
	 * 
	 * @return true 有效
	 */
	boolean isVaild();
	
	/**
	 * 是否登录
	 * 
	 * @return true 登录成功
	 */
	boolean isLogin();
	
	/**
	 * 是否关闭
	 * 
	 * @return true 关闭
	 */
	boolean isClose();
	
	/**
	 * 是否离线，接收服务器心跳超过最大超时
	 * 
	 * @return
	 */
	boolean isOffline();
	
	/**
	 * 接收到心跳后调用该方法，保存Session处于不离线状态
	 */
	void heartbeatTouch();
	
	/**
	 * 添加服务端接收消息到Session中处理
	 * 
	 * @param bytes
	 */
	void append(byte[] bytes);
	
	/**
	 * 发送消息
	 * 
	 * @param message 发送消息对象
	 * @throws IOException
	 */
	void send(RequestMessage message)throws IOException;
	
	/**
	 * 得到操作员key对象
	 * 
	 * @return
	 */
	OperatorKey getOperatorKey();
	
	/**
	 * 得到Session当前状态{@link Status}
	 * 
	 * @return
	 */
	Status getStatus();
}
