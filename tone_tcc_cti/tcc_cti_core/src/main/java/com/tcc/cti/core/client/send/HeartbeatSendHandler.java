package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Heartbeat;

import java.nio.charset.Charset;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

/**
 * 心跳发送
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class HeartbeatSendHandler extends AbstractSendHandler {
	private static final String HB_MSG = "<head>00013</head><msg>hb</msg>";
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return Heartbeat.isRequest(request.getMessageType());
	}

	@Override
	protected byte[] getMessage(Requestable<? extends Response> request,
			OperatorKey key, String seq, Charset charset) {
		
		return HB_MSG.getBytes(charset);
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			OperatorKey key, StringBuilder builder) {
		//None instance
	}
}
