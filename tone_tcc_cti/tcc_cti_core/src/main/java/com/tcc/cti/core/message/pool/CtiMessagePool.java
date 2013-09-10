package com.tcc.cti.core.message.pool;

import com.tcc.cti.core.message.receive.ReceiveMessage;

public interface CtiMessagePool {
	
	void push(String companyId,String opId,ReceiveMessage message);
	
	ReceiveMessage task(String companyId,String opId);
	
	void remove(String companyId,String opId);
	
	void startAutoClearExpire();
	
	void closeAutoClearExpire();
}
