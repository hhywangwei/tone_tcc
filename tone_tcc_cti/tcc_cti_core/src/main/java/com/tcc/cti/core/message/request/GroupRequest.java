package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.Group;

import com.tcc.cti.core.message.response.GroupResponse;

/**
 * 请求公司分组
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class GroupRequest extends MultiBaseRequest<GroupResponse>{
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
		builder.append("GroupRequest [_groupId=");
		builder.append(_groupId);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append(", _responses=");
		builder.append(_responses);
		builder.append("]");
		return builder.toString();
	}
}
