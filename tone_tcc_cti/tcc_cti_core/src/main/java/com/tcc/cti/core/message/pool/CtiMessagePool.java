package com.tcc.cti.core.message.pool;

import com.tcc.cti.core.message.response.ResponseMessage;

public interface CtiMessagePool {
	
	void push(String companyId,String opId,ResponseMessage message);
	
	ResponseMessage task(String companyId,String opId);
	
	void remove(String companyId,String opId);
	
	void startAutoClearExpire();
	
	void closeAutoClearExpire();
}
