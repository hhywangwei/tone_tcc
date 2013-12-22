package com.tcc.cti.driver.session.process.handler;

import java.io.IOException;
import java.nio.charset.Charset;

import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.sequence.GeneratorSeq;
import com.tcc.cti.driver.session.Sessionable;

/**
 * 发送消息到cti服务端处理。
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public interface SendHandlerable {
	
	/**
	 * 最大消息长度
	 */
	static final int MESSAGE_MAX_LENGTH = 2048;
	
	/**
	 * 根据指定编码发送消息
	 * 
	 * @param session {@link Sessionable} 连接服务端Session
	 * @param request 发送的消息对象
	 * @param generator 生成消息序列号
	 * @param Charset 发送对象字符集
	 * @throws IOException
	 */
	void send(Sessionable sesion, Requestable<? extends Response> request,
			GeneratorSeq generator,	Charset charset)throws IOException;
	
}
