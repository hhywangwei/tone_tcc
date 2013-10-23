package com.tcc.cti.core.client.monitor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
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
	private static final int DEFAULT_INIT_DELAY = 0;
	private static final int DEFAULT_DELAY = 20;
	
	private final OperatorChannel _channel;
	private final int _initDelay;
	private final int _delay;
	private final ScheduledExecutorService _executorService;
	
	private HeartbeatListener.HeartbeatEvent _event = new NoneHeartbeatEvent();
	
	public ScheduledHeartbeatKeep(OperatorChannel channel){
		this(channel,DEFAULT_INIT_DELAY,DEFAULT_DELAY);
	}
	
	public ScheduledHeartbeatKeep(OperatorChannel channel,int initDelay,int delay){
		_executorService = Executors.newSingleThreadScheduledExecutor();
		_initDelay = initDelay;
		_delay = delay;
		_channel = channel;
	}
	
	@Override
	public void start() {
		HeartbeatRunner runner = new HeartbeatRunner(_channel, _event);
		_executorService.scheduleWithFixedDelay(runner, _initDelay, _delay, TimeUnit.SECONDS);
	}
	
	@Override
	public void shutdown() {
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
		private final OperatorChannel _channel;
		private final ByteBuffer _buffer ;
		private final HeartbeatListener.HeartbeatEvent _event;
		
		public HeartbeatRunner(OperatorChannel channel,HeartbeatListener.HeartbeatEvent event){
			_channel = channel;
			_event = event;
			Charset c = Charset.forName(DEFAULT_CHARSET_NAME);
			byte[] m = heartbeatMessage.getBytes(c);
			_buffer = ByteBuffer.wrap(m);
		}
		
		@Override
		public void run() {
			sendHeartbeat(_channel);
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
