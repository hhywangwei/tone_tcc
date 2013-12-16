package com.tcc.cti.core.message.request;

import com.tcc.cti.core.message.MessageType;

/**
 * 请求移动座席取消对象
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 *
 */
public class MobileNumberCancelRequest extends RequestMessage{

	public MobileNumberCancelRequest() {
		super(MessageType.MobileNumberCancel.request());
	}

}
