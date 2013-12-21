package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.MobileNumber;

/**
 * 接受移动座席设置返回信息
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class MobileNumberReceiveHandler extends AbstractReceiveHandler{
	
	@Override
	protected boolean isReceive(String msgType) {
		return MobileNumber.isResponse(msgType);
	}

	@Override
	protected String getMessageType() {
		return MobileNumber.request();
	}

	 

}
