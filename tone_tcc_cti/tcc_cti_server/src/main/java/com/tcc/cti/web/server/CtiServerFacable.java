package com.tcc.cti.web.server;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.response.Response;

/**
 * CTI服务操作接口，外部程序访问CTI服务器统一接口，对CTI driver驱动封装，可方便客户端调用。
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public interface CtiServerFacable {
	
	/**
	 * 登录CTI服务器
	 * 
	 * @param operator 操作员信息
	 * @param password 密码
	 * @param opNumber 登录电话
	 * @param type 座席类型
	 * @exception {@link CtiServerException}
	 * @return 返回消息对象
	 */
	Response login(Operator operator,String password,String opNumber,String type)throws CtiServerException;
	
	/**
	 * 退出CTI服务器
	 * 
	 * @param operator 操作员信息
	 * @exception {@link CtiServerException}
	 * @return 返回消息对象
	 */
	Response logout(Operator operator)throws CtiServerException;
	
	
}
