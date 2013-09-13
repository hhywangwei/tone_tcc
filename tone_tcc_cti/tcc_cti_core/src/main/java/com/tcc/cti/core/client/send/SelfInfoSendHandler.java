package com.tcc.cti.core.client.send;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.MessageType;
import com.tcc.cti.core.message.send.SendMessage;

/**
 * 发送读取本座席信息
 * 
 * <pre>消息格式如下:
 * {@literal <head>00084</head><msg>per_worker_info</msg><seq>4</seq><WorkID></WorkID>}
 * <pre>消息格式说明
 * head:消息头，表示消息的长度，为五位固定长字符串，未满5位用“0”替补
 * msg:消息类型
 * seq:消息序号，通过该编号可使客户端发送消息和服务端返回信息关联
 * WorkID为空表示查座席本人信息，不为空表示查指定座席信息
 * 
 * {@code 是线程安全类}
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class SelfInfoSendHandler extends AbstractSendHandler  {
	private static final String MESSAGE_PATTER = "<msg>per_worker_info</msg><seq>%s</seq><WorkID></WorkID>";
	
	@Override
	protected boolean isSend(SendMessage message) {
		return message != null &&
				MessageType.SelfInfo.getType().equals(message.getMessageType());
	}

	@Override
	protected String buildMessage(SendMessage message,GeneratorSeq generator) {
		String m = String.format(MESSAGE_PATTER, generator.next());
		return m;
	}

}
