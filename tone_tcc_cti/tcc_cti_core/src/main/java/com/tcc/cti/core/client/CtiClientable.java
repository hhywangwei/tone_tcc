package com.tcc.cti.core.client;

import java.util.List;

import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.message.CtiMessage;

/**
 * cti服务客户端连接类，一个用户一个客户端
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface CtiClientable {
	
	
	/**
	 * 开始客户端连接服务
	 * 
	 * @throws ClientException
	 */
	void start()throws ClientException;

	/**
	 * 发送消息
	 * 
	 * @param message 消息对象
	 * @throws ClientException
	 */
	void send(CtiMessage message)throws ClientException;
	
	/**
	 * 关闭客户连接服务
	 * 
	 * @throws ClientException
	 */
	void close()throws ClientException;
	
	/**
	 * 设置接收消息处理
	 * 
	 * @param handlers
	 */
	void setReceiveHandlers(List<ReceiveHandler> handlers);
	
	/**
	 * 设置发送消息处理
	 * 
	 * @param handlers
	 */
	void setSendHandlers(List<SendHandler> handlers);
}
