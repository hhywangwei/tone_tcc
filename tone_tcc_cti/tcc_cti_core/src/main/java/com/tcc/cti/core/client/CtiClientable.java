package com.tcc.cti.core.client;

import java.util.List;

import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.message.send.SendMessage;

/**
 * cti服务客户端连接
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
	 * 关闭客户连接服务
	 * 
	 * @throws ClientException
	 */
	void close()throws ClientException;
	
	/**
	 * 注册用户到CTI服务，打开stocket连接
	 * 
	 * @param companyId 公司编号
	 * @param opId 操作员编号
	 * @throws ClientException
	 */
	void register(String companyId,String opId)throws ClientException;
	
	/**
	 * 注销用户CTI服务，关闭stocket连接
	 * 
	 * @param companyId 公司编号
	 * @param opId 操作编号
	 * @throws ClientException
	 */
	void unRegister(String companyId,String opId)throws ClientException;
	
	/**
	 * 发送消息
	 * 
	 * @param message 消息对象
	 * @throws ClientException
	 */
	void send(SendMessage message)throws ClientException;
	
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
	
	/**
	 * 设置连接超时
	 * 
	 * @param timeOut 单位秒
	 */
	void setTimeOut(int timeOut);

	/**
	 * 设置与cti服务器通信字符集编码
	 * 
	 * @param charset
	 */
	void setCharset(String charset);
}
