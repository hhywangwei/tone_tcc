package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.GroupInfo;

/**
 * 请求公司分组
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class GroupInfoRequest extends RequestMessage{

	public GroupInfoRequest() {
		super(GroupInfo.requestType());
	}

	@Override
	public String toString() {
		return "GroupInfoRequest [_messageType=" + _messageType
				+ ", _compayId=" + _compayId + "]";
	}
}
