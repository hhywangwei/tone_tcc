package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Rest;

import com.tcc.cti.core.message.response.Response;

/**
 * 请求休息
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class RestRequest extends BaseRequest<Response> {

	public RestRequest() {
		super(Rest.request());
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RestRequest [_messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
}
