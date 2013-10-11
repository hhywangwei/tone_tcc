package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Own;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.OwnRequest;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 发送读取本座席信息
 * 
 * <pre>消息格式如下:
 * {@literal <msg>per_worker_info</msg><seq>4</seq><WorkID></WorkID>}
 * msg:消息类型
 * seq:消息序号，通过该编号可使客户端发送消息和服务端返回信息关联
 * WorkID为空表示查座席本人信息，不为空表示查指定座席信息
 * 
 * {@code 是线程安全类}
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OwnSendHandler extends AbstractSendHandler{
	private static final Logger logger = LoggerFactory.getLogger(OwnSendHandler.class);
	private static final String WORK_ID_FORMAT = "<WorkID></WorkID>";
	
	@Override
	protected boolean isSend(RequestMessage message) {
		return message != null && 
				Own.isRequest(message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message,GeneratorSeq generator) {
		OwnRequest request = (OwnRequest)message;
		
		StringBuilder sb = new StringBuilder(128);
		sb.append(String.format(MSG_FORMAT, request.getMessageType()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(WORK_ID_FORMAT);
		
		
		String m = sb.toString();
		logger.debug("self info send is {}",m);
		
		return m;
	}

}
