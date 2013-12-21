package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Silence;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.request.SilenceRequest;
import com.tcc.cti.core.message.response.Response;
/**
 * 发送静音
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class SilenceSendHandler extends AbstractSendHandler{
	
	private static final String CALLLEG_FORMAT = "<CallLeg>%s</CallLeg>";
	private static final String FLAG_FORMAT = "<Flag>%s</Flag>";
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return Silence.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			OperatorKey key, StringBuilder builder) {
		
		SilenceRequest r = (SilenceRequest)request;
		buildOperator(key,builder);
		builder.append(String.format(CALLLEG_FORMAT, r.getCallLeg()));
		builder.append(String.format(FLAG_FORMAT, r.getFlag()));
	}

}
