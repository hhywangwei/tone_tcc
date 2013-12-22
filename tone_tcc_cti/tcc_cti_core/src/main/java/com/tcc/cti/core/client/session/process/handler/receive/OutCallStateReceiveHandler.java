package com.tcc.cti.core.client.session.process.handler.receive;

import static com.tcc.cti.core.message.MessageType.OutCallState;

import java.util.Map;

import com.tcc.cti.core.message.response.OutCallStateResponse;
import com.tcc.cti.core.message.response.Response;

/**
 * 接收外呼状态
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 *
 */
public class OutCallStateReceiveHandler extends AbstractReceiveHandler{
	private static final String STATE_PARAMETER = "State";
	private static final String CALL_LEG_PARAMETER = "CallLeg";
	
	@Override
	protected boolean isReceive(String msgType) {
		return OutCallState.isResponse(msgType);
	}
	
	@Override
	protected String getRequestMessageType(String msgType) {
		return OutCallState.request();
	}

	@Override
	protected Response buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		String callLeg = content.get(CALL_LEG_PARAMETER);
		String state = content.get(STATE_PARAMETER);
		return new OutCallStateResponse(seq,callLeg,state);
	}

}
