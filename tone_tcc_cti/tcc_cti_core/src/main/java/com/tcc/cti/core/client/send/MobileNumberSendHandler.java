package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.MobileNumber;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.MobileNumberRequest;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 实现发送移动座席消息
 * 
 * {@literal <msg>set_mobile_number</msg><seq>75</seq><CompanyID>11</CompanyID><OPID>2021</OPID><MobileNumber>6302</MobileNumber>}
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 *
 */
public class MobileNumberSendHandler extends AbstractSendHandler{
	
	private static final String NUMBER_FORMAT = "<MobileNumber>%s</MobileNumber>";
	
	@Override
	protected boolean isSend(RequestMessage message) {
		return MobileNumber.isRequest(message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message, OperatorKey key,
			GeneratorSeq generator) {
		
		MobileNumberRequest request = (MobileNumberRequest)message;
		StringBuilder sb = new StringBuilder(512);
		sb.append(String.format(MSG_FORMAT, request.getMessageType()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(COMPANY_ID_FORMAT,key.getCompanyId()));
		sb.append(String.format(OPID_FORMAT, key.getOpId()));
		sb.append(String.format(NUMBER_FORMAT, request.getNumber()));
		
		return sb.toString();
	}

}
