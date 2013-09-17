package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.ObtainMember;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.ObtainMemberRequest;
import com.tcc.cti.core.message.request.RequestMessage;

public class ObtainMemberSendHanlder extends AbstractSendHandler{
	private static final String MESSAGE_PATTER = 
			"<msg>worker_number_info</msg><seq>%s</seq>"
			+ "<CompanyID>%s</CompanyID><OPID>%s</OPID><GroupID>%s</GroupID>";
	
	@Override
	protected boolean isSend(RequestMessage message) {
		return ObtainMember.requestType().equals(message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message, GeneratorSeq generator) {
		String seq = generator.next();
		ObtainMemberRequest request = (ObtainMemberRequest)message;
		
		String m = String.format(MESSAGE_PATTER, seq,
				request.getCompayId(),request.getOpId(),
				request.getGroupId());
		
		return m;
	}

}
