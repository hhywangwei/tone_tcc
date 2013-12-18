package com.tcc.cti.core.message.response;

import static com.tcc.cti.core.message.MessageType.TransferGroup;

public class TransferGroupResponse extends SilenceResponse {

	public TransferGroupResponse(String companyId, String opId, String seq,
			String result) {
		super(companyId, opId,TransferGroup.response(), seq, result);
	}
}
