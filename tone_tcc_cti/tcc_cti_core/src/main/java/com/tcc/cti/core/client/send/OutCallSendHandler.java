package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.OutCall;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.OutCallRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

/**
 * 发送外呼消息
 * 
 * <pre>
 * {@literal<msg>outcall</msg><seq>107</seq><CompanyID>11</CompanyID><OPID>2021</OPID><Phone1>8001</Phone1><Phone2>8002</Phone2>}
 * msg:消息类型
 * seq:消息序号，通过该编号可使客户端发送消息和服务端返回信息关联
 * CompanyID：企业编号
 * OPID：工号
 * Phone1：座席号
 * Phone2:外呼电话号码
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallSendHandler extends AbstractSendHandler{
	private static final String OP_NUMBER_FORMAT = "<Phone1>%s</Phone1>";
	private static final String PHONE_FORMAT = "<Phone2>%s</Phone2>";
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return OutCall.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			OperatorKey key, StringBuilder builder) {
		
		OutCallRequest r = (OutCallRequest)request;
		
		buildOperator(key,builder);
		builder.append(String.format(OP_NUMBER_FORMAT, r.getOpNumber()));
		builder.append(String.format(PHONE_FORMAT, r.getPhone()));
	}
}
