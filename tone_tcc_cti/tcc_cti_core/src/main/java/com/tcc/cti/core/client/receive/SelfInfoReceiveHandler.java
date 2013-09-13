package com.tcc.cti.core.client.receive;

import java.util.Map;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.client.OperatorChannel;
import com.tcc.cti.core.message.MessageType;
import com.tcc.cti.core.message.pool.CtiMessagePool;

public class SelfInfoReceiveHandler extends AbstractReceiveHandler{

	@Override
	protected boolean isReceive(String msgType) {
		return MessageType.SelfInfo.getType().equals(msgType);
	}

	@Override
	protected void receiveHandler(CtiMessagePool pool, OperatorChannel channel,
			Map<String, String> content) throws ClientException {
		// TODO Auto-generated method stub
		
	}

}
