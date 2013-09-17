package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.SelfInfo;

import java.util.Map;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.client.OperatorChannel;
import com.tcc.cti.core.message.pool.CtiMessagePool;
import com.tcc.cti.core.message.response.SelfInfoResponse;

/**
 * 接收服务端发送的坐席信息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class SelfInfoReceiveHandler extends AbstractReceiveHandler{
	private static final String NAME_PARAMETER = "Name";
	private static final String GROUP_ATTRIBUTE_PARAMETER = "GroupAttribute";
	private static final String GROUP_STRING_PARAMETER = "GroupString";
	private static final String NUMBER_PARAMETER = "Number";
	private static final String LOGIN_STATE_PARAMETER = "LoginState";
	private static final String MOBILE_STATE_PARAMETER = "MobileState";
	private static final String STATE_PARAMETER = "State";
	private static final String BIND_STATE_PARAMETER = "BindState";
	private static final String CALL_STATE_PARAMETER = "CallState";
	private static final String TYPE_PARAMETER = "Type";
	private static final String MOBILE_NUMBER_PARAMETER = "MobileNumber";
	private static final String WORK_MODEL_PARAMETER = "WorkModel";
			

	@Override
	protected boolean isReceive(String msgType) {
		return SelfInfo.responseType().equals(msgType);
	}

	@Override
	protected void receiveHandler(CtiMessagePool pool, OperatorChannel channel,
			Map<String, String> content) throws ClientException {
		
		String companyId = channel.getOperatorKey().getCompanyId();
		String opId = channel.getOperatorKey().getOpId();
		String seq = content.get(SEQ_PARAMETER);
		
		SelfInfoResponse message = 
				buildMessage(companyId,opId,seq,content);
		
		pool.push(companyId, opId, message);
	}
	
	protected SelfInfoResponse buildMessage(String companyId,String opId,
			String seq,Map<String,String> content){
		
		return	new SelfInfoResponse.Builder(companyId,opId,seq).
				setBindState(content.get(BIND_STATE_PARAMETER)).
				setCallState(content.get(CALL_STATE_PARAMETER)).
				setGroupAttribute(content.get(GROUP_ATTRIBUTE_PARAMETER)).
				setGroupString(content.get(GROUP_STRING_PARAMETER)).
				setLoginState(content.get(LOGIN_STATE_PARAMETER)).
				setMobileNumber(content.get(MOBILE_NUMBER_PARAMETER)).
				setMobileState(content.get(MOBILE_STATE_PARAMETER)).
				setName(content.get(NAME_PARAMETER)).
				setNumber(content.get(NUMBER_PARAMETER)).
				setState(content.get(STATE_PARAMETER)).
				setType(content.get(TYPE_PARAMETER)).
				setWorkId(content.get(WORK_ID_PARAMETER)).
				setWorkModel(content.get(WORK_MODEL_PARAMETER)).
				build();
	}
}
