package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Record;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.RecordRequest;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 发送录音命令
 * 
 * <pre>消息格式如下:
 * {@literal <msg>start_record</msg><seq>107</seq><CompanyID>11</CompanyID><OPID>2021</OPID><CallLeg></CallLeg>}
 * msg:消息类型
 * seq:消息序号，通过该编号可使客户端发送消息和服务端返回信息关联
 * CompanyID：企业编号
 * OPID：工号
 * CallLeg：呼叫标识
 * 
 * {@code 是线程安全类}
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class RecordSendHandler extends AbstractSendHandler{
	private static final Logger logger = LoggerFactory.getLogger(RecordSendHandler.class);
	private static final String CALL_LEG_FORMAT = "<CallLeg>%s</CallLeg>";
	
	@Override
	protected boolean isSend(RequestMessage message) {
		return message != null && 
				Record.isRequest(message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message,OperatorKey key, GeneratorSeq generator) {
		RecordRequest r = (RecordRequest)message;
		
		StringBuilder builder = new StringBuilder(256);
		builder.append(String.format(MSG_FORMAT, r.getMessageType()));
		builder.append(String.format(SEQ_FORMAT, generator.next()));
		builder.append(String.format(COMPANY_ID_FORMAT, key.getCompanyId()));
		builder.append(String.format(OPID_FORMAT, key.getOpId()));
		builder.append(String.format(CALL_LEG_FORMAT, r.getCallLeg()));
		
		String m = builder.toString();
		logger.debug("record send is {}",m);
		
		return m;
	}

}
