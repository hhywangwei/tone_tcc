package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.MobileNumberCancel;

import com.tcc.cti.core.message.response.Response;

/**
 * 请求移动座席取消对象
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 *
 */
public class MobileNumberCancelRequest extends BaseRequest<Response>{

	public MobileNumberCancelRequest() {
		super(MobileNumberCancel.request());
	}
	
	public MobileNumberCancelRequest(int timeout){
		super(MobileNumberCancel.request(),timeout);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MobileNumberCancelRequest [_messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
}
