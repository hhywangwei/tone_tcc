package com.tcc.cti.core.message.pool;


import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.core.message.response.ResponseMessage;

/**
 * {@link OperatorCtiMessagePool}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OperatorCtiMessagePoolTest {
	
	@Test
	public void testMessageExpire()throws Exception{
		OperatorCtiMessagePool.MessageEntry e = 
				new OperatorCtiMessagePool.MessageEntry(2 * 1000, 1*1000);
		Thread.sleep(3 * 1000);
		Assert.assertTrue(e.expire());
		
		e.put(initMessage());
		Assert.assertFalse(e.expire());
	}
	
	@Test
	public void testPollMessageExpire()throws Exception{
		OperatorCtiMessagePool.MessageEntry e = 
				new OperatorCtiMessagePool.MessageEntry(2 * 1000, 1*1000);
		e.put(initMessage());
		Thread.sleep(3 * 1000);
		Assert.assertTrue(e.expire());
		
		ResponseMessage m = e.poll();
		Assert.assertNull(m);
		Assert.assertFalse(e.expire());
	}
	
	@Test
	public void testPushAndTask()throws Exception{
		OperatorCtiMessagePool pool = new OperatorCtiMessagePool();
		String companyId = "1";
		String opId = "1";
		
		ResponseMessage m =initMessage();
		pool.put(companyId, opId, m);
		
		Object om = pool.poll(companyId, opId);
		Assert.assertNotNull(om);
		Assert.assertEquals(m, om);
	}
	
	@Test
	public void testCompact()throws Exception{
		OperatorCtiMessagePool pool = new OperatorCtiMessagePool(2 * 1000, 1*1000);
		
		ResponseMessage m =initMessage();
		pool.put(m.getCompanyId(), m.getOpId(), m);
		Thread.sleep(3 * 1000);
		
		ResponseMessage m1 = new ResponseMessage(
				 "2", "2",m.getMessageType(),"2");
		pool.put(m1.getCompanyId(), m1.getOpId(), m1);
		
		pool.compact();
		
		ResponseMessage recevie = pool.poll(m.getCompanyId(), m.getCompanyId());
		Assert.assertNull(recevie);
		recevie = pool.poll(m1.getCompanyId(), m1.getCompanyId());
		Assert.assertEquals("2",recevie.getSeq());
	}
	
	private ResponseMessage initMessage(){
		return new ResponseMessage("1","1","login","1");
	}
}
