package com.tcc.cti.core.client.receive.handler;

import com.tcc.cti.core.client.ClientException;

public interface ReceiveHandler {
	
	void receive(String message)throws ClientException;
}
