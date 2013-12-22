package com.tcc.cti.core.client.session.process.handler.receive;

import static com.tcc.cti.core.message.MessageType.Call;

import java.util.Map;

import com.tcc.cti.core.message.response.CallResponse;
import com.tcc.cti.core.message.response.Response;

/**
 * 接受呼叫信息处理
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class CallReceiveHandler extends AbstractReceiveHandler{
	private static final String GROUP_ID_PARAMETER = "GroupID";
	private static final String CALL_LEG_PARAMETER = "CallLeg";
	private static final String CALLER_NUMBER_PARAMETER = "CallerNumber";
	private static final String ACCESS_NUMBER_PARAMETER = "AccessNumber";
	private static final String CALLED_NUMBER_PARAMETER = "CalledNumber";
	private static final String CALL_STATE_PARAMETER = "CallState";
	private static final String OP_NUMBER_PARAMETER = "OPNumber";
	private static final String NAME_PARAMETER = "Name";
	private static final String GLOBAL_CALL_LEG_PARAMETER = "GlobalCallLeg";
	private static final String RECORD_FLAG_PARAMETER = "RecordFlag";
	private static final String CALL_TYPE_PARAMETER = "CallType";
	private static final String PRE_OP_ID_PARAMETER = "PreOPID";
	private static final String PRE_OP_NUMBER_PARAMETER = "PreOPNumber";
	private static final String PRE_OP_NAME_PARAMETER = "PreOPName";
	private static final String USER_INPUT_PARAMETER = "UserInput";

	@Override
	protected boolean isReceive(String msgType) {
		return Call.isResponse(msgType);
	}
	
	@Override
	protected String getRequestMessageType(String msgType) {
		return Call.request();
	}

	@Override
	protected Response buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		
		return new CallResponse.Builder(seq).
				setGroupId(content.get(GROUP_ID_PARAMETER)).
				setCallLeg(content.get(CALL_LEG_PARAMETER)).
				setCallerNumber(content.get(CALLER_NUMBER_PARAMETER)).
				setAccessNumber(content.get(ACCESS_NUMBER_PARAMETER)).
				setCalledNumber(content.get(CALLED_NUMBER_PARAMETER)).
				setCallState(content.get(CALL_STATE_PARAMETER)).
				setOpNumber(content.get(OP_NUMBER_PARAMETER)).
				setName(content.get(NAME_PARAMETER)).
				setGlobalCallLeg(content.get(GLOBAL_CALL_LEG_PARAMETER)).
				setRecordFlag(content.get(RECORD_FLAG_PARAMETER)).
				setCallType(content.get(CALL_TYPE_PARAMETER)).
				setPreOpId(content.get(PRE_OP_ID_PARAMETER)).
				setPreOpNumber(content.get(PRE_OP_NUMBER_PARAMETER)).
				setPreOpName(content.get(PRE_OP_NAME_PARAMETER)).
				setUserInput(content.get(USER_INPUT_PARAMETER)).
				build();
	}
}
