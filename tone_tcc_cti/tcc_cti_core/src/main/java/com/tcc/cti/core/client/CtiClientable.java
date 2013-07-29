package com.tcc.cti.core.client;

import com.tcc.cti.core.message.CtiMessageable;

/**
 * cti服务客户端连接类，一个用户一个客户端
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface CtiClientable {
	
	/**
	 * 用户公司编号
	 * 
	 * @return
	 */
	String getCompanyId();
	
	/**
	 * 用户编号
	 * 
	 * @return
	 */
	String getOPId();

	/**
	 * 开始客户端连接服务
	 * 
	 * @throws ClientException
	 */
	void start()throws ClientException;

	/**
	 * 发送消息
	 * 
	 * @param message 消息对象{@link CtiMessageable}
	 * @throws ClientException
	 */
	void send(CtiMessageable message)throws ClientException;
	
	/**
	 * 关闭客户连接服务
	 * 
	 * @throws ClientException
	 */
	void close()throws ClientException;
}
