package com.tcc.cti.core.client.session.process;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.message.pool.OperatorCtiMessagePool;

/**
 * {@link SingleMessageProcess}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class SingleMessageProcessTest {
	
	@Test
	public void testPutButMessageNull()throws InterruptedException{
		SingleMessageProcess process = new SingleMessageProcess();
		Sessionable session = Mockito.mock(Sessionable.class);
		process.put(session, null);
		Assert.assertTrue(process.getQueueSize() == 0);
	}
	
	@Test
	public void testPut()throws InterruptedException{
		SingleMessageProcess process = new SingleMessageProcess();
		Sessionable session = Mockito.mock(Sessionable.class);
		process.put(session, "test");
		Assert.assertTrue(process.getQueueSize() == 1);
	}
	
	@Test
	public void testPutButFull()throws InterruptedException{
		final SingleMessageProcess process = new SingleMessageProcess(10);
		Sessionable session = Mockito.mock(Sessionable.class);
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				try{
					Thread.sleep(2 * 1000);
					process.task();
				}catch(InterruptedException e){
					Assert.fail(e.toString());
				}
			}
		});
		t.start();
		int len = 11;
		int i = 0;
		for(;i < len;i++){
			process.put(session, "test" + String.valueOf(i));
		}
		Assert.assertTrue(i == 11);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testStartButMessagePoolIsNull(){
		SingleMessageProcess process = new SingleMessageProcess();
		process.setReceiveHandlers(new ArrayList<ReceiveHandler>());
		process.start();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testStartButReceiveHandlersIsNull(){
		SingleMessageProcess process = new SingleMessageProcess();
		process.setMessagePool(new OperatorCtiMessagePool());
		process.start();
	}
	
	@Test
	public void testStart(){
		SingleMessageProcess process = new SingleMessageProcess();
		process.setReceiveHandlers(new ArrayList<ReceiveHandler>());
		process.setMessagePool(new OperatorCtiMessagePool());
		process.start();
		Assert.assertTrue(process.isStart());
		process.close();
	}
}
