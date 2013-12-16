package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.MobileNumberCancel;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.MobileNumberCancelRequest;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 实现发送取消移动座席消息
 * 
 * {@literal <msg>cancel_mobile_number</msg><seq>92</seq><CompanyID>11</CompanyID><OPID>2021</OPID>}
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 *
 */
public class MobileNumberCancelSendHandler extends AbstractSendHandler{

	@Override
	protected boolean isSend(RequestMessage message) {
		return MobileNumberCancel.isRequest(message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message, OperatorKey key,
			GeneratorSeq generator) {

		MobileNumberCancelRequest request = (MobileNumberCancelRequest)message;
		
		StringBuilder sb = new StringBuilder(512);
		sb.append(String.format(MSG_FORMAT, request.getMessageType()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(COMPANY_ID_FORMAT,key.getCompanyId()));
		sb.append(String.format(OPID_FORMAT, key.getOpId()));
		
		return sb.toString();
	}
}
