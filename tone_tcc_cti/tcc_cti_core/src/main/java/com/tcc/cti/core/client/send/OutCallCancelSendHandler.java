package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.OutCallCancel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.OutCallCancelRequest;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 发送取消外呼消息
 * 
 * <pre>
 * {@literal<msg>outcallcancel</msg><seq>107</seq><CompanyID>11</CompanyID><OPID>2021</OPID><CallLeg></CallLeg>}
 * 
 * msg:消息类型
 * seq:消息序号，通过该编号可使客户端发送消息和服务端返回信息关联
 * CompanyID：企业编号
 * OPID：工号
 * CallLeg： 呼叫标识
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallCancelSendHandler extends AbstractSendHandler{
	private static final Logger logger = LoggerFactory.getLogger(OutCallCancelSendHandler.class);
	private static final String CALL_LEG_FORMAT = "<CallLeg>%s</CallLeg>";

	@Override
	protected boolean isSend(RequestMessage message) {
		return message != null && 
				message.getMessageType().equals(
						OutCallCancel.request());
	}

	@Override
	protected String buildMessage(RequestMessage message, GeneratorSeq generator) {
		
        OutCallCancelRequest request = (OutCallCancelRequest)message;
		
		StringBuilder sb = new StringBuilder(128);
		sb.append(String.format(MSG_FORMAT, OutCallCancel.request()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(COMPANY_ID_FORMAT, request.getCompayId()));
		sb.append(String.format(OPID_FORMAT, request.getOpId()));
		sb.append(String.format(CALL_LEG_FORMAT, request.getCallLeg()));
		
		String m = sb.toString();
		
		logger.debug("Send out call is {}",m);
		
		return m;
	}

}
