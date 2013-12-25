package com.tcc.cti.driver.session.process.handler.send;

import static com.tcc.cti.driver.message.MessageType.Heartbeat;

import java.nio.charset.Charset;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;

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
	protected byte[] getMessage(Phone phone,Requestable<? extends Response> request,
			Operator key, String seq, Charset charset) {
		
		return HB_MSG.getBytes(charset);
	}

	@Override
	protected void buildMessage(Phone phone,Requestable<? extends Response> request,
			Operator key, StringBuilder builder) {
		//None instance
	}
}
