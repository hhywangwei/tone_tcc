package com.tcc.cti.core.message.pool;

import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.core.message.receive.ReceiveMessage;

/**
 * {@link OperatorCtiMessagePool}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OperatorCtiMessagePoolTest {

	@Test
	public void testMessageExpire()throws Exception{
		String companyId = "1";
		String opId = "1";
		OperatorCtiMessagePool.MessagePoolKey key = 
				new OperatorCtiMessagePool.MessagePoolKey(companyId, opId, 2 * 1000);
		Thread.sleep(3 * 1000);
		Assert.assertTrue(key.expire());
	}
	
	@Test
	public void testPushAndTask(){
		OperatorCtiMessagePool pool = new OperatorCtiMessagePool();
		String companyId = "1";
		String opId = "1";
		
		ReceiveMessageImpl m = new ReceiveMessageImpl();
		pool.push(companyId, opId, m);
		
		Object om = pool.task(companyId, opId);
		Assert.assertNotNull(om);
		Assert.assertEquals(m, om);
	}
	
	@Test
	public void testRemove(){
		OperatorCtiMessagePool pool = new OperatorCtiMessagePool();
		String companyId = "1";
		String opId = "1";
		
		ReceiveMessageImpl m = new ReceiveMessageImpl();
		pool.push(companyId, opId, m);
		
		pool.remove(companyId, opId);
		Object om = pool.task(companyId, opId);
		Assert.assertNull(om);
	}
	
	@Test
	public void testAutoClearExpire()throws Exception{
		OperatorCtiMessagePool pool = new OperatorCtiMessagePool(20 * 1000);
		String companyId = "1";
		String opId = "1";
		ReceiveMessageImpl m = new ReceiveMessageImpl();
		pool.push(companyId, opId, m);
		
		try{
			pool.startAutoClearExpire();
			Thread.sleep(36* 1000);
			Object om = pool.task(companyId, opId);
			Assert.assertNull(om);
		}finally{
			pool.closeAutoClearExpire();
		}
	}
	
	private class ReceiveMessageImpl extends ReceiveMessage{
		public ReceiveMessageImpl(){
			super("1","1","login","1");
		}
	}
}
