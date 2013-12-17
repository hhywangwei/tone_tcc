package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Resume;

public class ResumeRequest extends RequestMessage {

	public ResumeRequest() {
		super(Resume.request());
	}

	
}
