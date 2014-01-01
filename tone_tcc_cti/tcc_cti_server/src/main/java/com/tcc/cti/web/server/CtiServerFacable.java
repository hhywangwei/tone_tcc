package com.tcc.cti.web.server;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.response.Response;

public interface CtiServerFacable {
	
	Response login(Operator operator,String password,String opNumber);
}
