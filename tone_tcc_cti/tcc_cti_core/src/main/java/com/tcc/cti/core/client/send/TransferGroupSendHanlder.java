package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.TransferGroup;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.request.TransferGroupRequest;
import com.tcc.cti.core.message.response.Response;

public class TransferGroupSendHanlder extends AbstractSendHandler {
	private static final String CALLLEG_FORMAT = "<CallLeg>%s</CallLeg>";
	private static final String GROUPID_FORMAT = "<GroupID>%s</GroupID>";

	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return TransferGroup.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			OperatorKey key, StringBuilder builder) {
		
		TransferGroupRequest r = (TransferGroupRequest)request;
		buildOperator(key,builder);
		builder.append(String.format(CALLLEG_FORMAT, r.getCallLeg()));
		builder.append(String.format(GROUPID_FORMAT,r.getGroupId()));
	}
}
