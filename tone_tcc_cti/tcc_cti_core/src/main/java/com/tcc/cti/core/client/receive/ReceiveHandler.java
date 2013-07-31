package com.tcc.cti.core.client.receive;

import com.tcc.cti.core.client.ClientException;

public interface ReceiveHandler {
	
	void receive(String message)throws ClientException;
}
