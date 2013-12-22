package com.tcc.cti.driver.message.request;

import static com.tcc.cti.driver.message.MessageType.Monitor;

import com.tcc.cti.driver.message.response.MonitorResponse;
/**
 * 获得班长信息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class MonitorRequest extends BaseRequest<MonitorResponse>{

	public MonitorRequest() {
		super(Monitor.request());
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MonitorRequest [_messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
}
