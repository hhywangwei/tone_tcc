package com.tcc.cti.core.client.connection;

import java.io.IOException;

import com.tcc.cti.core.client.OperatorChannel;

public interface Connectionable {
	
	boolean connect(OperatorChannel oc)throws IOException;
}
