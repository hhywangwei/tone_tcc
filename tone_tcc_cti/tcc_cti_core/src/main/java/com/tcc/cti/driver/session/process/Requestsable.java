package com.tcc.cti.driver.session.process;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.event.RequestEvent;
import com.tcc.cti.driver.message.response.Response;

public interface Requestsable extends RequestEvent {

	void recevie(Operator operator,String seq,Response response);
}
