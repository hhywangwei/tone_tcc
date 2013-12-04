package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Heartbeat;

import java.nio.charset.Charset;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 心跳发送
 * 
 * @author <a href="hhywangwei@gmail.com">
 */
public class HeartbeatSendHandler extends AbstractSendHandler {
	private static final String HB_MSG = "<head>00013</head><msg>hb</msg>";
	
	@Override
	protected boolean isSend(RequestMessage message) {
		return message != null &&
				Heartbeat.isRequest(message.getMessageType());
	}

	@Override
	protected byte[] getMessage(RequestMessage message,GeneratorSeq generator,Charset charset) {
		return HB_MSG.getBytes(charset);
	}
	
	@Override
	protected String buildMessage(RequestMessage message, GeneratorSeq generator) {
		//None instance
		return null;
	}
}
