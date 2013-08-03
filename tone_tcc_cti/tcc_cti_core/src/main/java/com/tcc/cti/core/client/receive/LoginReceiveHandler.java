package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.*;

import java.util.Map;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.message.pool.CtiMessagePool;

public class LoginReceiveHandler extends AbstractReceiveHandler{
	
	@Override
	protected boolean isReceive(String msgType) {
		return msgType.equals(Login.getType());
	}

	@Override
	protected void receiveHandler(Map<String, String> content,
			CtiMessagePool pool) throws ClientException {
		
	}

}
