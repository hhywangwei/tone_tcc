package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.GroupMember;

/**
 * 获取公司分组成员信息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class GroupMemberRequest extends RequestMessage {
	
	private String _groupId;
	
	public GroupMemberRequest(){
		super(GroupMember.request());
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
		builder.append("ObtainMemberSendMessage [_groupId=");
		builder.append(_groupId);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append(", _compayId=");
		builder.append(_compayId);
		builder.append(", _opId=");
		builder.append(_opId);
		builder.append("]");
		return builder.toString();
	}
}
