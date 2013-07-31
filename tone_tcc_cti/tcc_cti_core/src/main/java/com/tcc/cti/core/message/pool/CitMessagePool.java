package com.tcc.cti.core.message.pool;

public interface CitMessagePool {
	
	void push(String companyId,String opId,Object message);
	
	Object task(String companyId,String opId);
}
