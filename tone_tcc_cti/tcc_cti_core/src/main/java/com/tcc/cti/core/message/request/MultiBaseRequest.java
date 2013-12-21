package com.tcc.cti.core.message.request;

import com.tcc.cti.core.message.response.Response;

public class MultiBaseRequest<T extends Response> extends BaseRequest<T> {
	private static final String COMPLETE_PREFIX = "-";
	public MultiBaseRequest(String messageType) {
		super(messageType);
	}
	
	public MultiBaseRequest(String messageType,int timeout){
		super(messageType,timeout,-1);
	}
	
	@Override
	protected boolean isComplete(T response){
		String seq = response.getSeq();
		if(seq == null){
			return false;
		}
		return seq.startsWith(COMPLETE_PREFIX);
	}
}
