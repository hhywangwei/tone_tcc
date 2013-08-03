package com.tcc.cti.core.client.sequence;

/**
 * 生成发送消息序列号类
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface GeneratorSeq {
	
	/**
	 * 生辰序号
	 * 
	 * @return
	 */
	String next();
	
}
