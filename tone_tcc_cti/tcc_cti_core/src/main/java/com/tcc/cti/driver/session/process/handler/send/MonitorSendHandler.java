package com.tcc.cti.driver.session.process.handler.send;

import static com.tcc.cti.driver.message.MessageType.Monitor;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;

/**
 * 发送获得班长信息
 *
 * <pre>
 * {@literal <msg>monitor_info</msg><seq>25</seq><CompanyID>11</CompanyID>}
 * msg:消息类型
 * seq:消息序号，通过该编号可使客户端发送消息和服务端返回信息关联
 * CompanyID：企业编号
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class MonitorSendHandler extends AbstractSendHandler {

    @Override
    protected boolean isSend(Requestable<? extends Response> request) {
        return Monitor.isRequest(request.getMessageType());
    }

    @Override
    protected void buildMessage(Phone phone, Requestable<? extends Response> request,
        Operator key, StringBuilder builder) {

        String companyId = String.format(COMPANY_ID_FORMAT, key.getCompanyId());
        builder.append(companyId);
    }
}
