package com.tcc.cti.driver.session.process;

import java.io.IOException;

import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.sequence.GeneratorSeq;
import com.tcc.cti.driver.session.Sessionable;
import com.tcc.cti.driver.session.process.handler.receive.ParseMessageException;

/**
 * CTI服务器消息处理接口
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public interface MessageProcessable {
	
	/**
	 * 发送消息处理
	 * 
	 * @param session
	 * @param request
	 * @param generator
	 * @throws IOException
	 */
	void sendProcess(Sessionable session, Requestable<? extends Response> request,
			GeneratorSeq generator)throws IOException;
	
	/**
	 * 接收消息进行处理
	 * 
	 * @param session {@link Sessionable}
	 * @param m 接收消息
	 * @throws ParseMessageException
	 */
	void receiveProcess(Sessionable session, String m)throws ParseMessageException;
	
}
