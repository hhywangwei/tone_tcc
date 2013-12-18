package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Silence;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.RequestMessage;
import com.tcc.cti.core.message.request.SilenceRequest;
/**
 * 发送静音
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class SilenceSendHandler extends AbstractSendHandler{
	private static final String CALLLEG_FORMAT = "<CallLeg>%s</CallLeg>";
	private static final String FLAG_FORMAT = "<Flag>%s</Flag>";
	
	@Override
	protected boolean isSend(RequestMessage message) {
		return Silence.isRequest(message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message, OperatorKey key,
			GeneratorSeq generator) {
		
		SilenceRequest request = (SilenceRequest)message;
		StringBuilder sb = new StringBuilder(512);
		sb.append(String.format(MSG_FORMAT, request.getMessageType()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(COMPANY_ID_FORMAT,key.getCompanyId()));
		sb.append(String.format(OPID_FORMAT, key.getOpId()));
		sb.append(String.format(CALLLEG_FORMAT, request.getCallLeg()));
		sb.append(String.format(FLAG_FORMAT, request.getFlag()));
		
		return sb.toString();
	}

}
