package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Silence;

/**
 * 接收服务端静消息处理
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class SilenceReceiveHandler extends AbstractReceiveHandler{

	@Override
	protected boolean isReceive(String msgType) {
		return Silence.isResponse(msgType);
	}

	@Override
	protected String getMessageType() {
		return Silence.request();
	}
}
