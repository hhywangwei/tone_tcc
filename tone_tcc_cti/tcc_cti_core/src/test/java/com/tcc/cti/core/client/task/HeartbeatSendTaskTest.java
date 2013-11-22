package com.tcc.cti.core.client.task;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.monitor.event.HeartbeatEvent;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.message.request.HeartbeatRequest;

/**
 * {@link HeartbeatSendTask}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class HeartbeatSendTaskTest {

	@Test
	public void testRunSuccess()throws Exception{
		Sessionable session = initSession();
		
		HeartbeatSendTask t = new HeartbeatSendTask(session);
		AtomicInteger count =new AtomicInteger(0);
		AtomicInteger errorCount =new AtomicInteger(0);
		HeartbeatEvent event = initEvent(count,errorCount);
		t.setEvent(event);
		
		t.run();
		verify(session,atLeast(1)).send(any(HeartbeatRequest.class));
		Assert.assertTrue(count.intValue() == 1);
		Assert.assertTrue(errorCount.intValue() == 0);
	}
	
	@Test
	public void testRunError()throws Exception{
        Sessionable session = initSession();
        when(session.isVaild()).thenReturn(true);
        HeartbeatSendTask t = new HeartbeatSendTask(session);
		AtomicInteger count =new AtomicInteger(0);
		AtomicInteger errorCount =new AtomicInteger(0);
		HeartbeatEvent event = initEvent(count,errorCount);
		t.setEvent(event);
		doThrow(new IOException("mock exception"))
        .when(session).send(any(HeartbeatRequest.class));
		t.run();
		verify(session,atLeast(1)).send(any(HeartbeatRequest.class));
		Assert.assertTrue(count.intValue() == 0);
		Assert.assertTrue(errorCount.intValue() == 1);
	}
	
	private Sessionable initSession(){
		Sessionable session = mock(Sessionable.class);
		when(session.isVaild()).thenReturn(true);
		OperatorKey key = new OperatorKey("1", "1");
		when(session.getOperatorKey()).thenReturn(key);
		return session;
	}
	
	private HeartbeatEvent initEvent(
			final AtomicInteger count,final AtomicInteger errorCount){
		return new HeartbeatEvent(){
			@Override
			public void success() {
				count.incrementAndGet();
			}
			@Override
			public void fail(Throwable e) {
				errorCount.incrementAndGet();
			}
		};
	}
}
