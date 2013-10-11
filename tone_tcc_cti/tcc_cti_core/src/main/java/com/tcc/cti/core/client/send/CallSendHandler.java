package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Call;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.CallRequest;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 发送获取呼叫信息的信息,登录时同步与服务器呼叫信息（如断线重新登录）。
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class CallSendHandler extends AbstractSendHandler {
	private static final Logger logger = LoggerFactory.getLogger(CallSendHandler.class);

	@Override
	protected boolean isSend(RequestMessage message) {
		return message != null && 
				Call.isRequest(message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message, GeneratorSeq generator) {
		CallRequest request = (CallRequest)message;
		
		StringBuilder sb = new StringBuilder(128);
		sb.append(String.format(MSG_FORMAT, request.getMessageType()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(COMPANY_ID_FORMAT, request.getCompayId()));
		sb.append(String.format(OPID_FORMAT, request.getOpId()));
		
		String m = sb.toString();
		logger.debug("Send phone call is {}",m);
		
		return m;
	}

}
