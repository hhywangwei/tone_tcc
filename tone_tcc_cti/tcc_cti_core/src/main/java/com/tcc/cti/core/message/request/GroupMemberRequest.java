package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.GroupMember;

import com.tcc.cti.core.message.response.GroupMemberResponse;

/**
 * 获取公司分组成员信息
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class GroupMemberRequest extends MultiBaseRequest<GroupMemberResponse> {
	private String _groupId;
	
	public GroupMemberRequest(){
		super(GroupMember.request());
	}
	
	public GroupMemberRequest(int timeout){
		super(GroupMember.request(),timeout);
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
		builder.append("GroupMemberRequest [_groupId=");
		builder.append(_groupId);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
}
