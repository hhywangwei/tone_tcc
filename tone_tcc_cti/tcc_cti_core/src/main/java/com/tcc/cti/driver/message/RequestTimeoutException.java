package com.tcc.cti.driver.message;

import com.tcc.cti.driver.message.token.Tokenable;

@SuppressWarnings("serial")
public class RequestTimeoutException extends Exception{
	private static final String DEFAULT_SEQ = "";
	private static final String MESSAGE_FORMAT = "Request token {} is time out";
	
	public RequestTimeoutException(){
		super(DEFAULT_SEQ);
	}

	public RequestTimeoutException(Tokenable token){
		super(String.format(MESSAGE_FORMAT, token.token()));
	}
	
}
