package com.tcc.cti.core.message;

@SuppressWarnings("serial")
public class RequestTimeoutException extends Exception{
	private static final String DEFAULT_SEQ = "";
	private static final String MESSAGE_FORMAT = "Request seq {} is time out";
	
	public RequestTimeoutException(){
		super(DEFAULT_SEQ);
	}

	public RequestTimeoutException(String seq){
		super(String.format(MESSAGE_FORMAT, seq));
	}
	
}
