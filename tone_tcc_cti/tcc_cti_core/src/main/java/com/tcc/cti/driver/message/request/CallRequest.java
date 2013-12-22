package com.tcc.cti.driver.message.request;

import static com.tcc.cti.driver.message.MessageType.Call;

import com.tcc.cti.driver.message.response.CallResponse;

/**
 * 发送获取呼叫信息对象
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class CallRequest extends BaseRequest<CallResponse>{

	public CallRequest() {
		super(Call.request());
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CallRequest [_messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}

}
