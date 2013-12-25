package com.tcc.cti.driver.session.process.handler.receive;
import static com.tcc.cti.driver.message.MessageType.CloseCall;

import java.util.Map;

import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;
import com.tcc.cti.driver.session.Sessionable;
import com.tcc.cti.driver.session.Sessionable.UpdatePhoneCallBack;
import com.tcc.cti.driver.session.process.Requestsable;

public class CloseCallReceiveHandler extends AbstractReceiveHandler{
	private static final String CALL_LEG_PARAMETER = "CallLeg";

//	private static final String GROUP_ID_PARAMETER = "GroupID";
//	private static final String CALLER_NUMBER_PARAMETER = "CallerNumber";
//	private static final String ACCESS_NUMBER_PARAMETER = "AccessNumber";
//	private static final String CALLED_NUMBER_PARAMETER = "CalledNumber";
//	private static final String RELEASE_REASON_PARAMETER = "ReleaseReason";

	@Override
	protected boolean isReceive(String msgType) {
		return CloseCall.isResponse(msgType);
	}
	
	@Override
	protected void receiveHandler(Requestsable requests, Sessionable session,
			Map<String, String> content) {
		
		final String callLeg = content.get(CALL_LEG_PARAMETER);
		
		session.updatePhone(new UpdatePhoneCallBack(){
			@Override
			public void update(Phone phone) {
				phone.closeCall(callLeg);
			}
		});
	}

	@Override
	protected Response buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		
		//TODO none instance
	    return null;
	}

}
