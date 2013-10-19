package com.tcc.cti.core.message.pool;

import com.tcc.cti.core.message.response.ResponseMessage;

/**
 * 接收服务端消息池，客户端可通过消息池得到指定客户接收的消息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface CtiMessagePool {
	
	/**
	 * 装入接收消息
	 * 
	 * @param companyId 公司编号
	 * @param opId      操作员编号
	 * @param message   消息
	 * @throws Exception 插入消息异常
	 */
	void put(String companyId,String opId,ResponseMessage message)throws Exception;
	
	/**
	 * 得到服务返回消息，消息必须未过期。
	 * 
	 * @param companyId 公司编号
	 * @param opId      操作员编号
	 * @return          服务端消息
	 * @throws Exception  得到消息异常
	 */
	ResponseMessage poll(String companyId,String opId)throws Exception;
	
	 /**
	  * 压缩消息池，回收已经过期的消息池（用户已经下线）
	  */
	 void compact();
}
