package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Monitor;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;
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
public class MonitorSendHandler extends AbstractSendHandler{
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return Monitor.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			OperatorKey key, StringBuilder builder) {
		
		String companyId = String.format(COMPANY_ID_FORMAT, key.getCompanyId());
		builder.append(companyId);
	}
}
