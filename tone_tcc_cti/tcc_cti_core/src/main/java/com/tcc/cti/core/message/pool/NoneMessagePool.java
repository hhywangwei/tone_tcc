package com.tcc.cti.core.message.pool;

/**
 * 实现消息缓存池，该是一个空类什么也不处理。
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class NoneMessagePool implements CtiMessagePool{

	@Override
	public void push(String companyId, String opId, Object message) {
		
	}

	@Override
	public Object task(String companyId, String opId) {
		return null;
	}

}
