package com.tcc.cti.core.client.session;

import java.io.IOException;

import com.tcc.cti.core.client.OperatorKey;

/**
 * 操作用户注册服务Session异常类
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class SessionRegisterException extends IOException{

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE_FORMAT = "CompanyID is %s and OpId is %s, register session faile.";
	
	public SessionRegisterException(OperatorKey key,Throwable t){
		super(formatMessage(key),t);
	}
	
	public SessionRegisterException(OperatorKey key,String m){
		super(formatMessage(key) + " " + m);
	}
	
	private static String formatMessage(OperatorKey key){
		return String.format(MESSAGE_FORMAT, key.getCompanyId(),key.getOpId());
	}
}
