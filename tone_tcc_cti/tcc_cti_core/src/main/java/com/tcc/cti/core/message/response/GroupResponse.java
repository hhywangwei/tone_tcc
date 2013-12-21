package com.tcc.cti.core.message.response;


/**
 * 接受公司分组情况
 * 
 * <pre>
 * _groupId:用户组编号(1－32之间的一个正数)
 * _groupName:用户名
 * _maxQueue:最大排队人数
 * _groupWorkState:组工作状态
 * _chooseOpType:选择话务员的方式
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class GroupResponse extends Response{
	
	public static class Builder{
		private final String _seq;
		private String _groupId;
		private String _groupName;
		private String _maxQueue;
		private String _groupWorkState;
		private String _chooseOpType;
		
		public Builder(String seq){
			_seq = seq;
		}
		
		public Builder setGroupId(String groupId){
			_groupId = groupId;
			return this;
		}
		
		public Builder setGroupName(String groupName){
			_groupName = groupName;
			return this;
		}
		
		public Builder setMaxQueue(String maxQueue){
			_maxQueue = maxQueue;
			return this;
		}
		
		public Builder setGroupWorkState(String groupWorkState){
			_groupWorkState = groupWorkState;
			return this;
		}
		
		public Builder setChooseOpType(String chooseOpType){
			_chooseOpType = chooseOpType;
			return this;
		}
		
		public GroupResponse build(){
			
			GroupResponse response = new GroupResponse(_seq);
			response.setGroupId(_groupId);
			response.setGroupName(_groupName);
			response.setMaxQueue(_maxQueue);
			response.setGroupWorkState(_groupWorkState);
			response.setChooseOpType(_chooseOpType);
			
			return response;
		}
	}

	private String _groupId;
	private String _groupName;
	private String _maxQueue;
	private String _groupWorkState;
	private String _chooseOpType;
	
	private GroupResponse(String seq) {
		
		super( seq,SUCCESS_RESULT);
	}
	
	public String getGroupId(){
		return _groupId;
	}
	
	private void setGroupId(String groupId){
		_groupId = groupId;
	}
	
	public String getGroupName(){
		return _groupName;
	}
	
	private void setGroupName(String groupName){
		_groupName = groupName;
	}
	
	public String getMaxQueue(){
		return _maxQueue;
	}
	
	private void setMaxQueue(String maxQueue){
		_maxQueue = maxQueue;
	}
	
	public String getGroupWorkState(){
		return _groupWorkState;
	}
	
	private void setGroupWorkState(String groupWorkState){
		_groupWorkState = groupWorkState;
	}
	
	public String getChooseOpType(){
		return _chooseOpType;
	}
	
	private void setChooseOpType(String chooseOpType){
		_chooseOpType = chooseOpType;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("GroupResponse [_groupId=");
		builder2.append(_groupId);
		builder2.append(", _groupName=");
		builder2.append(_groupName);
		builder2.append(", _maxQueue=");
		builder2.append(_maxQueue);
		builder2.append(", _groupWorkState=");
		builder2.append(_groupWorkState);
		builder2.append(", _chooseOpType=");
		builder2.append(_chooseOpType);
		builder2.append(", _seq=");
		builder2.append(_seq);
		builder2.append("]");
		return builder2.toString();
	}
}
