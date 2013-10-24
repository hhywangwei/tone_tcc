package com.tcc.cti.core.client.monitor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.OperatorChannel;
import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.monitor.HeartbeatListener.HeartbeatEvent;
import com.tcc.cti.core.message.pool.OperatorCtiMessagePool;

public class ScheduledHeartbeatKeepTest {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledHeartbeatKeepTest.class);
	 
	
	@Test
	public void testStart()throws IOException{
		String host = "211.136.173.132";
		int port = 9999;
		
		SocketChannel channel = SocketChannel.open();
		InetSocketAddress address = new InetSocketAddress(host, port);
		channel.configureBlocking(true);
		channel.connect(address);
		
		OperatorKey key = new OperatorKey("1", "1");
		OperatorChannel oc =new OperatorChannel.Builder(
				key,channel,new OperatorCtiMessagePool()).build();
		
		ScheduledHeartbeatKeep keep = new ScheduledHeartbeatKeep(oc);
		final AtomicInteger count =new AtomicInteger(0);
		keep.listener(new HeartbeatEvent(){

			@Override
			public void success(ByteBuffer buffer) {
				logger.debug("Send is success");
				count.incrementAndGet();
			}

			@Override
			public void fail(ByteBuffer buffer, Throwable e) {
				count.incrementAndGet();
				Assert.fail(e.toString());
			}
		});
		
		try{
			keep.start();
			Thread.sleep(40 * 1000);
			if(count.get() < 1){
				Assert.fail("Heartbeat is not start");
			}
		}catch(InterruptedException e){
			logger.error("Sleep is interruptedException:{}",e.toString());
			Assert.fail("Sleep is interruptedException");
		}finally{
			keep.shutdown();
		}
	}
}
