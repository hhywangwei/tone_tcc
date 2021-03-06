package com.tcc.cti.driver.session.process.handler.send;

import static com.tcc.cti.driver.message.MessageType.TransferGroup;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.request.TransferGroupRequest;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;

/**
 * 转接到组消息发送
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class TransferGroupSendHanlder extends AbstractSendHandler {

    private static final String CALLLEG_FORMAT = "<CallLeg>%s</CallLeg>";
    private static final String GROUPID_FORMAT = "<GroupID>%s</GroupID>";

    @Override
    protected boolean isSend(Requestable<? extends Response> request) {
        return TransferGroup.isRequest(request.getMessageType());
    }

    @Override
    protected void buildMessage(Phone phone, Requestable<? extends Response> request,
        Operator key, StringBuilder builder) {

        TransferGroupRequest r = (TransferGroupRequest) request;
        buildOperator(key, builder);
        builder.append(String.format(CALLLEG_FORMAT, phone.getCallLeg()));
        builder.append(String.format(GROUPID_FORMAT, r.getGroupId()));
    }
}
