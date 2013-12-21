package com.tcc.cti.core.client.session.process;

import java.io.IOException;
import java.nio.charset.Charset;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

/**
 * 服务器消息处理接口
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public interface MessageProcessable {
	
	void sendProcess(Sessionable session,Requestable<? extends Response> request,
			GeneratorSeq generator,Charset charset)throws IOException;
	
	/**
	 * 推送接收消息进行处理
	 * 
	 * @param session {@link Sessionable}
	 * @param m 接收消息
	 * @throws InterruptedException
	 */
	void receiveProcess(Sessionable session,String m)throws InterruptedException;
	
	/**
	 * 开始消息处理
	 */
	void start();
	
	/**
	 * 关闭消息处理
	 */
	void close();
	
	/**
	 * 判断消息处理开始
	 * 
	 * @return {@value true}开始消息处理
	 */
	boolean isStart();
}
