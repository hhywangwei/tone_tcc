package com.tcc.cti.driver.session.process.handler.send;

import static com.tcc.cti.driver.message.MessageType.Silence;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.request.SilenceRequest;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;
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
	protected void buildMessage(Phone phone,Requestable<? extends Response> request,
			Operator key, StringBuilder builder) {
		
		SilenceRequest r = (SilenceRequest)request;
		buildOperator(key,builder);
		builder.append(String.format(CALLLEG_FORMAT, phone.getCallLeg()));
		builder.append(String.format(FLAG_FORMAT, r.getFlag()));
	}

}
