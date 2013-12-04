package com.tcc.cti.core.client;

import java.util.List;

import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.client.session.Sessionable.Status;
import com.tcc.cti.core.message.pool.CtiMessagePool;
import com.tcc.cti.core.message.request.RequestMessage;

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
	 * 注册用户到CTI服务，打开socket连接
	 * 
	 * @param companyId 公司编号
	 * @param opId 操作员编号
	 * @return true:注册成功,false:注册失败
	 * @throws ClientException
	 */
	boolean register(String companyId,String opId)throws ClientException;
	
	/**
	 * 注销用户CTI服务，关闭socket连接
	 * 
	 * @param companyId 公司编号
	 * @param opId 操作编号
	 * @throws ClientException
	 */
	void unRegister(String companyId,String opId)throws ClientException;
	
	/**
	 * 得到客户连接状态
	 * 
	 * @param companyId 公司编号
	 * @param opId      操作员编号
	 * @return
	 */
	Status getStatus(String companyId,String opId);
	
	/**
	 * 发送消息
	 * 
	 * @param message 消息对象
	 * @throws ClientException
	 */
	void send(RequestMessage message)throws ClientException;
	
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
	 * 得到接收服务器消息池
	 * 
	 * @return
	 */
	CtiMessagePool getMessagePool();
}
