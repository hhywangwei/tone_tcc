package com.tcc.cti.driver.session.process.handler.send;

import static com.tcc.cti.driver.message.MessageType.CallHelp;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.CallHelpRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;

/**
 * 发送呼叫帮助
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class CallHelpSendHandler extends AbstractSendHandler {

    private static final String CALLLEG_FORMAT = "<CallLeg>%s</CallLeg>";
    private static final String TRANSFER_WORKID_FORMAT = "<TransferWorkID>%s</TransferWorkID>";
    private static final String TRANSFER_NUMBER_FORMAT = "<TransferNumber>%s</TransferNumber>";
    private static final String STATUS_FORMAT = "<Status>%s</Status>";

    @Override
    protected boolean isSend(Requestable<? extends Response> request) {
        return CallHelp.isRequest(request.getMessageType());
    }

    @Override
    protected void buildMessage(Phone phone, Requestable<? extends Response> request,
        Operator key, StringBuilder builder) {

        CallHelpRequest r = (CallHelpRequest) request;
        buildOperator(key, builder);
        builder.append(String.format(CALLLEG_FORMAT, phone.getCallLeg()));
        builder.append(String.format(TRANSFER_WORKID_FORMAT, r.getTransferWorkId()));
        builder.append(String.format(TRANSFER_NUMBER_FORMAT, r.getTransferNumber()));
        builder.append(String.format(STATUS_FORMAT, r.getStatus()));
    }

}
