package com.tcc.cti.core.client.session;

import java.io.IOException;

import com.tcc.cti.core.client.OperatorKey;

/**
 * 访问服务器异常，用户登录失败后继续向服务发送指令则产生该异常
 * 
 * @author WangWei
 */
public class SessionAccessException extends IOException{
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE_FORMAT = "CompanyID %s and OpId %s, user not access cti server.";
	
	public SessionAccessException(OperatorKey key){
		super(formatMessage(key));
	}
	
	private static String formatMessage(OperatorKey key){
		return String.format(MESSAGE_FORMAT, key.getCompanyId(),key.getOpId());
	}
}
