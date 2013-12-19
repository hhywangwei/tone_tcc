package com.tcc.cti.core.message.response;

import static com.tcc.cti.core.message.MessageType.Logout;

public class LogoutResponse extends SilenceResponse{

	public LogoutResponse(String companyId, String opId, 
			String seq,String result) {
		super(companyId, opId, Logout.response(), seq,result);
	}

}
