package com.tcc.cti.core.message.response;

import static com.tcc.cti.core.message.MessageType.Status;

public class StatusResponse extends SilenceResponse{

	public StatusResponse(String companyId, String opId, String seq,
			String result) {
		super(companyId, opId, Status.response(), seq, result);
	}
}
