package com.tcc.cti.core.client.task;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.OperatorChannel;
import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.monitor.event.HeartbeatEvent;
import com.tcc.cti.core.client.monitor.event.NoneHeartbeatEvent;

/**
 * 发送心跳任务
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class HeartbeatSendTask implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(HeartbeatSendTask.class);
	
	private static final String DEFAULT_CHARSET_NAME = "ISO-8859-1";
	private final String heartbeatMessage = "<head>00013</head><msg>hb</msg>";
	private final OperatorChannel _channel;
	private final ByteBuffer _buffer ;
	private HeartbeatEvent _event = new NoneHeartbeatEvent();
	
	public HeartbeatSendTask(OperatorChannel channel){
		this(channel,DEFAULT_CHARSET_NAME);
	}
	
	public HeartbeatSendTask(OperatorChannel channel, String charset){
		_channel = channel;
		Charset c = Charset.forName(charset);
		byte[] m = heartbeatMessage.getBytes(c);
		_buffer = ByteBuffer.wrap(m);
	}
	
	@Override
	public void run() {
		if(_channel.isStart()){
			sendHeartbeat(_channel);
		}
	}
	
	private void sendHeartbeat(OperatorChannel channel){
		try{
			logger.debug("{} send hb......",channel.getOperatorKey());
			SocketChannel socketChannel = channel.getChannel();
			socketChannel.write(_buffer);	
			_event.success(_buffer);
		}catch(IOException e){
			OperatorKey ok = channel.getOperatorKey();
			logger.error("companyId={} opId={} Heartbeat send is error:{}",
					ok.getCompanyId(),ok.getOpId(),e.getMessage());
			_event.fail(_buffer, e);
		}
	}
	
	public void setEvent(HeartbeatEvent event){
		_event = event;
	}
}