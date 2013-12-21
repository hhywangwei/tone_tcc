package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.MobileNumberCancel;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

/**
 * 实现发送取消移动座席消息
 * 
 * {@literal <msg>cancel_mobile_number</msg><seq>92</seq><CompanyID>11</CompanyID><OPID>2021</OPID>}
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 *
 */
public class MobileNumberCancelSendHandler extends AbstractSendHandler{

	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return MobileNumberCancel.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			OperatorKey key, StringBuilder builder) {

		buildOperator(key,builder);
	}
}
