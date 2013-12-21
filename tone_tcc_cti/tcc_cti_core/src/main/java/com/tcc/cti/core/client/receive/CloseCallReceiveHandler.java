package com.tcc.cti.core.client.receive;
import static com.tcc.cti.core.message.MessageType.CloseCall;

import java.util.Map;

import com.tcc.cti.core.message.response.CloseCallResponse;
import com.tcc.cti.core.message.response.Response;

public class CloseCallReceiveHandler extends AbstractReceiveHandler{
	private static final String GROUP_ID_PARAMETER = "GroupID";
	private static final String CALL_LEG_PARAMETER = "CallLeg";
	private static final String CALLER_NUMBER_PARAMETER = "CallerNumber";
	private static final String ACCESS_NUMBER_PARAMETER = "AccessNumber";
	private static final String CALLED_NUMBER_PARAMETER = "CalledNumber";
	private static final String RELEASE_REASON_PARAMETER = "ReleaseReason";

	@Override
	protected boolean isReceive(String msgType) {
		return CloseCall.isResponse(msgType);
	}
	
	@Override
	protected String getMessageType() {
		return CloseCall.request();
	}

	@Override
	protected Response buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		
		return new CloseCallResponse.Builder(seq).
				setGroupId(content.get(GROUP_ID_PARAMETER)).
				setCalledNumber(content.get(CALLED_NUMBER_PARAMETER)).
				setCallerNumber(content.get(CALLER_NUMBER_PARAMETER)).
				setCallLeg(content.get(CALL_LEG_PARAMETER)).
				setAccessNumber(content.get(ACCESS_NUMBER_PARAMETER)).
				setReleaseReason(content.get(RELEASE_REASON_PARAMETER)).
				build();
	}

}
