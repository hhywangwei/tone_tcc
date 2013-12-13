package com.tcc.cti.core.client.send;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 发送消息到cti服务端处理。
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface SendHandler {
	
	/**
	 * 最大消息长度
	 */
	static final int MESSAGE_MAX_LENGTH = 2048;
	
	/**
	 * 根据指定编码发送消息
	 * 
	 * @param channel {@link SocketChannel}
	 * @param key {@link OperatorKey} 操作员Key
	 * @param message 发送的消息对象
	 * @param generator 生成消息序列号
	 * @param Charset 发送对象字符集
	 * @throws IOException
	 */
	void send(SocketChannel channel, OperatorKey key, RequestMessage message,
			GeneratorSeq generator, Charset charset)throws IOException;
	
}
