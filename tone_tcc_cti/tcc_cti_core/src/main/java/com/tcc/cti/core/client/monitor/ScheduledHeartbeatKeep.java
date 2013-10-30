package com.tcc.cti.core.client.monitor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
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
	
	private Object _monitor = new Object();
	private OperatorChannel _channel;
	private int _initDelay;
	private int _delay;
	private ScheduledExecutorService _executorService;
	private volatile ScheduledFuture<?> _future ;
	private volatile boolean _independent = false;
	
	private HeartbeatListener.HeartbeatEvent _event = new NoneHeartbeatEvent();
	
	public ScheduledHeartbeatKeep(OperatorChannel channel){
		init(channel,null,DEFAULT_INIT_DELAY,DEFAULT_DELAY);
	}
	
	public ScheduledHeartbeatKeep(OperatorChannel channel,
			ScheduledExecutorService executorService,int initDelay,int delay){
		
		init(channel,executorService,initDelay,delay);
	}
	
	private void init(OperatorChannel channel,
			ScheduledExecutorService executorService,int initDelay,int delay){
	 
		_channel = channel;
		_executorService = executorService;
		_initDelay = initDelay;
		_delay = delay;
	}
	
	@Override
	public void start() {
		synchronized (_monitor) {
			if(_executorService == null){
				_executorService = Executors.newSingleThreadScheduledExecutor();
				_independent = true;
			}
			HeartbeatRunner runner = new HeartbeatRunner(_channel, _event);
			_future = _executorService.scheduleWithFixedDelay(
					runner, _initDelay, _delay, TimeUnit.SECONDS);	
		}
	}
	
	@Override
	public void shutdown() {
		synchronized (_monitor) {
			if(_future == null){
				return ;
			}
			
			_future.cancel(true);
			
			if(_independent){
				_executorService.shutdownNow();		
			}
		}
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
				if(!channel.isStart()){
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
