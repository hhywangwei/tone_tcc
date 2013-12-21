package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Heartbeat;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.client.session.process.Requestsable;

/**
 * 接收服务器心跳信息
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class HeartbeatReceiveHandler extends AbstractReceiveHandler{
	private static final Logger logger = LoggerFactory.getLogger(HeartbeatReceiveHandler.class);

	@Override
	protected boolean isReceive(String msgType) {
		return Heartbeat.isRequest(msgType);
	}
	
	@Override
	protected String getMessageType() {
		return Heartbeat.request();
	}
	
	@Override
	protected void receiveHandler(Requestsable requests, Sessionable session,
			Map<String, String> content) {
		
		logger.debug("Receive {} hb.....",session.getOperatorKey());
		session.heartbeatTouch();
	}
}
