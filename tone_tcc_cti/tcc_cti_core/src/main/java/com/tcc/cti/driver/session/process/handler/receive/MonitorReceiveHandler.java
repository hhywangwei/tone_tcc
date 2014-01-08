package com.tcc.cti.driver.session.process.handler.receive;

import static com.tcc.cti.driver.message.MessageType.Monitor;

import java.util.Map;

import com.tcc.cti.driver.message.response.MonitorResponse;
import com.tcc.cti.driver.message.response.Response;

/**
 * 接受班长信息
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class MonitorReceiveHandler extends AbstractReceiveHandler {

    private static final String NAME_PARAMETER = "Name";
    private static final String NUMBER_PARAMETER = "Number";
    private static final String GROUP_ATTRIBUTE_PARAMETER = "GroupAttribute";
    private static final String GROUP_STRING_PARAMETER = "GroupString";
    private static final String LOGIN_STATE_PARAMETER = "LoginState";
    private static final String MOBILE_STATE_PARAMETER = "MobileState";
    private static final String STATE_PARAMETER = "State";
    private static final String BIND_STATE_PARAMETER = "BindState";
    private static final String CALL_STATE_PARAMETER = "CallState";

    @Override
    protected boolean isReceive(String msgType) {
        return Monitor.isResponse(msgType);
    }

    @Override
    protected String getRequestMessageType(String msgType) {
        return Monitor.request();
    }

    @Override
    protected Response buildMessage(String companyId, String opId,
        String seq, Map<String, String> content) {

        return new MonitorResponse.Builder(seq).
            setBindState(content.get(BIND_STATE_PARAMETER)).
            setCallState(content.get(CALL_STATE_PARAMETER)).
            setGroupAttribute(content.get(GROUP_ATTRIBUTE_PARAMETER)).
            setGroupString(content.get(GROUP_STRING_PARAMETER)).
            setLoginState(content.get(LOGIN_STATE_PARAMETER)).
            setMobileState(content.get(MOBILE_STATE_PARAMETER)).
            setName(content.get(NAME_PARAMETER)).
            setNumber(content.get(NUMBER_PARAMETER)).
            setState(content.get(STATE_PARAMETER)).
            setWorkId(content.get(WORK_ID_PARAMETER)).
            build();
    }
}
