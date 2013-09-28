package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Monitor;
/**
 * 获得班长信息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class MonitorRequest extends RequestMessage{

	public MonitorRequest() {
		super(Monitor.request());
	}

}
