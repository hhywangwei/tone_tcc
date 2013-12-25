package com.tcc.cti.driver.session.process.handler.send;

import static com.tcc.cti.driver.message.MessageType.OutCallCancel;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;

/**
 * 发送取消外呼消息
 * 
 * <pre>
 * {@literal<msg>outcallcancel</msg><seq>107</seq><CompanyID>11</CompanyID><OPID>2021</OPID><CallLeg></CallLeg>}
 * 
 * msg:消息类型
 * seq:消息序号，通过该编号可使客户端发送消息和服务端返回信息关联
 * CompanyID：企业编号
 * OPID：工号
 * CallLeg： 呼叫标识
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallCancelSendHandler extends AbstractSendHandler{
	private static final String CALL_LEG_FORMAT = "<CallLeg>%s</CallLeg>";

	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return OutCallCancel.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Phone phone,Requestable<? extends Response> request,
			Operator key, StringBuilder builder) {
		
		buildOperator(key,builder);
		builder.append(String.format(CALL_LEG_FORMAT, phone.getCallLeg()));
	}

}
