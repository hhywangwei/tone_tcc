package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Call;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

/**
 * 发送获取呼叫信息的信息,登录时同步与服务器呼叫信息（如断线重新登录）。
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class CallSendHandler extends AbstractSendHandler {

	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return 	Call.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			OperatorKey key, StringBuilder builder) {
		
		buildOperator(key,builder);
	}
}
