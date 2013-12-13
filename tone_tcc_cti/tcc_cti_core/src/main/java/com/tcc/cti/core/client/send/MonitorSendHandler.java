package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.MonitorRequest;
import com.tcc.cti.core.message.request.RequestMessage;
/**
 * 发送获得班长信息
 * 
 * <pre>
 * {@literal <msg>monitor_info</msg><seq>25</seq><CompanyID>11</CompanyID>}
 * msg:消息类型
 * seq:消息序号，通过该编号可使客户端发送消息和服务端返回信息关联
 * CompanyID：企业编号
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class MonitorSendHandler extends AbstractSendHandler{
	private static final Logger logger = LoggerFactory.getLogger(MonitorSendHandler.class);
	
	@Override
	protected boolean isSend(RequestMessage message) {
		return message != null && 
				Monitor.isRequest(message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message, OperatorKey key, GeneratorSeq generator) {
		MonitorRequest request = (MonitorRequest)message;
		
		StringBuilder sb = new StringBuilder(128);
		sb.append(String.format(MSG_FORMAT, request.getMessageType()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(COMPANY_ID_FORMAT, key.getCompanyId()));
		
		String m = sb.toString();
		logger.debug("Send get monitor is {}",m);
		return m;
	}

}
