package com.tcc.cti.web.server;

@SuppressWarnings("serial")
public class CtiServerException extends Exception{
	
	public CtiServerException(Throwable e){
		super(e);
	}
	
	public CtiServerException(String msg,Throwable e){
		super(msg,e);
	}
}
