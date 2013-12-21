package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.OutCallCancel;

/**
 * 接收取消外呼状态
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallCancelReceiveHandler extends AbstractReceiveHandler{
	
	@Override
	protected boolean isReceive(String msgType) {
		return OutCallCancel.isResponse(msgType);
	}

	@Override
	protected String getMessageType() {
		return OutCallCancel.request();
	}
}
