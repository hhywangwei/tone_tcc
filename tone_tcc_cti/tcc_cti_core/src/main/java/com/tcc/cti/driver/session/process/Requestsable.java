package com.tcc.cti.driver.session.process;

import com.tcc.cti.driver.message.event.RequestEvent;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.message.token.Tokenable;

public interface Requestsable extends RequestEvent {

	void recevie(Tokenable token,Response response);
}
