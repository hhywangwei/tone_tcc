package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.MobileNumberCancel;

/**
 * 接受取消移动座席设置返回信息
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class MobileNumberCancelReceiveHandler extends AbstractReceiveHandler{
	
	@Override
	protected boolean isReceive(String msgType) {
		return MobileNumberCancel.isResponse(msgType);
	}

	@Override
	protected String getMessageType() {
		return MobileNumberCancel.request();
	}
}
