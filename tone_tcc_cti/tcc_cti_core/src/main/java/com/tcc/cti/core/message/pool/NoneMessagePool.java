package com.tcc.cti.core.message.pool;

import com.tcc.cti.core.message.receive.ReceiveMessage;

/**
 * 实现消息缓存池，该是一个空类什么也不处理。
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class NoneMessagePool implements CtiMessagePool{

	@Override
	public void push(String companyId, String opId, ReceiveMessage message) {
		//none instance
	}

	@Override
	public ReceiveMessage task(String companyId, String opId) {
		return null;
	}

	@Override
	public void remove(String companyId, String opId) {
		//none instance
	}

	@Override
	public void startAutoClearExpire() {
		//none instance
	}

	@Override
	public void closeAutoClearExpire() {
		//none instance
	}

}
