package com.tcc.cti.core.client.send;

import java.nio.channels.SocketChannel;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.message.CtiMessage;

/**
 * 发送消息到cti服务端处理。
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface SendHandler<T extends CtiMessage> {
	/**
	 * 发送消息缺省编码
	 */
	static final String DEFAULT_CHARTSET = "ISO-8859-1";
	
	/**
	 * 最大消息长度
	 */
	static final int MESSAGE_MAX_LENGTH = 2048;
	
	/**
	 * 发送的消息，
	 * 
	 * @param channel {@link SocketChannel}
	 * @param message 发送的消息
	 * @return
	 */
	void send(SocketChannel channel,T message)throws ClientException;
	
	/**
	 * 根据指定编码发送消息
	 * 
	 * @param channel {@link SocketChannel}
	 * @param message 发送的消息对象
	 * @param charset 发送对象字符集
	 * @throws ClientException
	 */
	void send(SocketChannel channel,T message,String charset)throws ClientException;
	
}
