package com.tcc.cti.core.client.sequence;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生成发送消息序列号 ，序号为从0开始递增的整数
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 *
 */
public class MemoryGeneratorSeq implements GeneratorSeq{
	
	private final String companyId;
	private final String opId;
	private final AtomicInteger count =new AtomicInteger(0);
	
	public MemoryGeneratorSeq(String companyId,String opId){
		this.companyId = companyId;
		this.opId = opId;
	}

	@Override
	public String next() {
		return String.valueOf(count.incrementAndGet());
	}
	
	public String getCompanyId(){
		return this.companyId;
	}
	
	public String getOpId(){
		return this.opId;
	}
}
