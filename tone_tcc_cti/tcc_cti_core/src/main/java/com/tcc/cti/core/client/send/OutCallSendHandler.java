package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.OutCall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.OutCallRequest;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 发送外呼消息
 * 
 * <pre>
 * {@literal<msg>outcall</msg><seq>107</seq><CompanyID>11</CompanyID><OPID>2021</OPID><Phone1>8001</Phone1><Phone2>8002</Phone2>}
 * msg:消息类型
 * seq:消息序号，通过该编号可使客户端发送消息和服务端返回信息关联
 * CompanyID：企业编号
 * OPID：工号
 * Phone1：座席号
 * Phone2:外呼电话号码
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallSendHandler extends AbstractSendHandler{
	private static final Logger logger = LoggerFactory.getLogger(OutCallSendHandler.class);
	private static final String OP_NUMBER_FORMAT = "<Phone1>%s</Phone1>";
	private static final String PHONE_FORMAT = "<Phone2>%s</Phone2>";
	
	@Override
	protected boolean isSend(RequestMessage message) {
		return message != null && 
				OutCall.request().equals(
						message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message, GeneratorSeq generator) {
		
		OutCallRequest request = (OutCallRequest)message;
		
		StringBuilder sb = new StringBuilder(128);
		sb.append(String.format(MSG_FORMAT, OutCall.request()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(COMPANY_ID_FORMAT, request.getCompayId()));
		sb.append(String.format(OPID_FORMAT, request.getOpId()));
		sb.append(String.format(OP_NUMBER_FORMAT, request.getOpNumber()));
		sb.append(String.format(PHONE_FORMAT, request.getPhone()));
		
		String m = sb.toString();
		logger.debug("Send out call is {}",m);
		
		return m;
	}

}
