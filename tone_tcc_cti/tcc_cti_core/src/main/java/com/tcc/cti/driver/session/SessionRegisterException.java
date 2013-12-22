package com.tcc.cti.driver.session;

import java.io.IOException;

import com.tcc.cti.driver.Operator;

/**
 * 操作用户注册服务Session异常类
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class SessionRegisterException extends IOException{

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE_FORMAT = "CompanyID is %s and OpId is %s, register session faile.";
	
	public SessionRegisterException(Operator key,Throwable t){
		super(formatMessage(key),t);
	}
	
	public SessionRegisterException(Operator key,String m){
		super(formatMessage(key) + " " + m);
	}
	
	private static String formatMessage(Operator key){
		return String.format(MESSAGE_FORMAT, key.getCompanyId(),key.getOpId());
	}
}
