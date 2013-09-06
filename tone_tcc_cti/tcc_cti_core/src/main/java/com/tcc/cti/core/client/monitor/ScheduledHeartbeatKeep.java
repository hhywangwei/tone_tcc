package com.tcc.cti.core.client.monitor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.OperatorChannel;

/**
 * 实现心跳程序
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class ScheduledHeartbeatKeep implements HeartbeatKeepable, HeartbeatListener{
	private static final Logger logger = LoggerFactory.getLogger(ScheduledHeartbeatKeep.class);
	private static final int DEFAULT_POOL_SIZE = 1;
	
	private final ScheduledExecutorService _executorService;
	private final List<OperatorChannel> _channels = 
			Collections.synchronizedList(new ArrayList<OperatorChannel>());
	private HeartbeatListener.HeartbeatEvent _event = new NoneHeartbeatEvent();
	
	public ScheduledHeartbeatKeep(){
		_executorService = Executors.newScheduledThreadPool(DEFAULT_POOL_SIZE);
	}
	
	@Override
	public void start() {
		HeartbeatRunner runner = new HeartbeatRunner(_channels, _event);
		_executorService.scheduleWithFixedDelay(runner, 2, 20, TimeUnit.SECONDS);
	}

	@Override
	public boolean register(OperatorChannel channel) {
		synchronized (_channels) {
			if(_channels.contains(channel)){
				if(channel.isOpen()){
					return false;	
				}
				_channels.remove(channel);
			}
			_channels.add(channel);
			return true;
		}
	}

	@Override
	public void unRegister(OperatorChannel channel) {
		synchronized (_channels) {
			_channels.remove(channel);
		}
	}
	
	@Override
	public boolean contains(OperatorChannel channel) {
		synchronized (_channels) {
			return _channels.contains(channel);
		}
	}
	
	@Override
	public void shutdown() {
		synchronized (_channels) {
			_channels.clear();
		}
		_executorService.shutdown();
	}
	
	@Override
	public void listener(HeartbeatEvent event){
		_event = event;
	}
	
	/**
	 * 发送心跳运行
	 * 
	 * @author <a href="hhywangwei@gmail.com">wangwei</a>
	 *
	 */
	private static class HeartbeatRunner implements Runnable{
		private static final String DEFAULT_CHARSET_NAME = "ISO-8859-1";
		private final String heartbeatMessage = "<head>00013</head><msg>hb</msg>";
		private final List<OperatorChannel> _channels;
		private final ByteBuffer _buffer ;
		private final HeartbeatListener.HeartbeatEvent _event;
		
		public HeartbeatRunner(List<OperatorChannel> channels,HeartbeatListener.HeartbeatEvent event){
			_channels = channels;
			_event = event;
			Charset c = Charset.forName(DEFAULT_CHARSET_NAME);
			byte[] m = heartbeatMessage.getBytes(c);
			_buffer = ByteBuffer.wrap(m);
		}
		
		@Override
		public void run() {
			synchronized (_channels) {
				for(OperatorChannel channel : _channels){
					sendHeartbeat(channel);
				}
			}
		}
		
		private void sendHeartbeat(OperatorChannel channel){
			try{
				if(!channel.isOpen()){
					return;
				}
				SocketChannel socketChannel = channel.getChannel();
				socketChannel.write(_buffer);	
				_event.success(_buffer);
			}catch(IOException e){
				String companyId = channel.getOperatorKey().getCompanyId();
				String opId = channel.getOperatorKey().getOpId();
				logger.error("companyId={} opId={} Heartbeat send is error:{}",
						companyId,opId,e.getMessage());
				_event.fail(_buffer, e);
			}
		}
	}
	
	private final static class NoneHeartbeatEvent implements HeartbeatListener.HeartbeatEvent{

		@Override
		public void success(ByteBuffer buffer) {
			// none instance
			
		}

		@Override
		public void fail(ByteBuffer buffer, Throwable e) {
			// none instance
		}
	}

}
