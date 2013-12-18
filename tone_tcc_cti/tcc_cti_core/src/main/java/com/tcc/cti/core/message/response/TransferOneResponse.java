package com.tcc.cti.core.message.response;

import static com.tcc.cti.core.message.MessageType.TransferOne;

public class TransferOneResponse extends SilenceResponse {

	public TransferOneResponse(String companyId, String opId, String seq,
			String result) {
		super(companyId, opId, TransferOne.response(),seq, result);
	}

}
