package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.OutCall;

/**
 * 收到外呼信息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallReceiveHandler extends AbstractReceiveHandler{
	
	@Override
	protected boolean isReceive(String msgType) {
		return OutCall.isResponse(msgType);
	}

	@Override
	protected String getMessageType() {
		return OutCall.request();
	}

	 
}
