package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Heartbeat;

import java.util.Map;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.client.OperatorChannel;
import com.tcc.cti.core.message.pool.CtiMessagePool;
import com.tcc.cti.core.message.response.ResponseMessage;

/**
 * 接收服务器心跳信息
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class HeartbeatReceiveHandler extends AbstractReceiveHandler{

	@Override
	protected boolean isReceive(String msgType) {
		return Heartbeat.isRequest(msgType);
	}
	
	@Override
	protected void receiveHandler(CtiMessagePool pool, OperatorChannel channel,
			Map<String, String> content) throws ClientException {
		
		channel.heartbeatTouch();
	}

	@Override
	protected ResponseMessage buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		// none instance
		return null;
	}

}
