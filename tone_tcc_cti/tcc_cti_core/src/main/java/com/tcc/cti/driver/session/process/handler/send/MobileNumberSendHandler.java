package com.tcc.cti.driver.session.process.handler.send;

import static com.tcc.cti.driver.message.MessageType.MobileNumber;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.MobileNumberRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;

/**
 * 实现发送移动座席消息
 * 
 * {@literal <msg>set_mobile_number</msg><seq>75</seq><CompanyID>11</CompanyID><OPID>2021</OPID><MobileNumber>6302</MobileNumber>}
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 *
 */
public class MobileNumberSendHandler extends AbstractSendHandler{
	private static final String NUMBER_FORMAT = "<MobileNumber>%s</MobileNumber>";
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return MobileNumber.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Phone phone,Requestable<? extends Response> request,
			Operator key, StringBuilder builder) {
		
		MobileNumberRequest r = (MobileNumberRequest)request;
		buildOperator(key,builder);
		builder.append(String.format(NUMBER_FORMAT, r.getNumber()));
	}

}
