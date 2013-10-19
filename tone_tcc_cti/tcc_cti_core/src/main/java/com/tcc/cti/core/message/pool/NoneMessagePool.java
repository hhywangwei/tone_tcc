package com.tcc.cti.core.message.pool;

import com.tcc.cti.core.message.response.ResponseMessage;

/**
 * 实现消息缓存池，该是一个空类什么也不处理。
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class NoneMessagePool implements CtiMessagePool{

	@Override
	public void put(String companyId, String opId, ResponseMessage message) {
		//none instance
	}

	@Override
	public ResponseMessage poll(String companyId, String opId) {
		return null;
	}

	@Override
	public void compact() {
		// TODO Auto-generated method stub
		
	}
}
