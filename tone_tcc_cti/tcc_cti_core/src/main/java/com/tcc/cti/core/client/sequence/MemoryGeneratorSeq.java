package com.tcc.cti.core.client.sequence;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生成发送消息序列号,格式 = 公司编号 × 10000
 * 
 * @author wang
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
		return companyId + opId +String.valueOf(count.decrementAndGet());
	}

}
