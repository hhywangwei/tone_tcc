package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Group;

/**
 * 请求公司分组
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class GroupRequest extends RequestMessage{
	private String _groupId;

	public GroupRequest() {
		super(Group.request());
	}
	
	public void setGroupId(String groupId){
		_groupId = groupId;
	}
	
	public String getGroupId(){
		return _groupId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GroupInfoRequest [_groupId=");
		builder.append(_groupId);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append("]");
		return builder.toString();
	}
}
