package com.tcc.cti.core.message.request;

import com.tcc.cti.core.message.MessageType;

/**
 * 请求休息
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class RestRequest extends RequestMessage {

	public RestRequest() {
		super(MessageType.Rest.request());
	}

}
