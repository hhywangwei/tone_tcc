package com.tcc.cti.core.message.response;

import static com.tcc.cti.core.message.MessageType.ObtainMember;

/**
 * 获取组成员信息
 * 
 * <pre>
 * _name:姓名
 * _number:座席电话号码
 * _groupAttribute:分组属性(int类型32个bit对应32个组，一个座席人员可以属于多个组，相应bit为1)
 * _mobileNumber:移动座席电话号码
 * _workModel:工作模式(0 – 普通模式,1 – ACW模式)
 * _groupString:分组的新的表示方式(以逗号分开的多个组)
 * _loginState:登录状态
 * _bindState:绑定状态
 * _callState:呼叫状态
 * _mobileState:移动座席状态
 * _recordFlag:1－有录音属性，0－没有
 * _recordingNow:1-正在被录音，0-没有
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class ObtainMemberResponse extends ResponseMessage {
	
	public static class Builder{
		private final String _companyId;
		private final String _opId;
		private final String _messageType;
		private final String _seq;
		private String _workId;
		private String _name;
		private String _groupAttribute;
		private String _groupString;
		private String _number;
		private String _loginState;
		private String _mobileState;
		private String _state;
		private String _bindState;
		private String _callState;
		private String _mobileNumber;
		private String _workModel;
		private String _recordFlag;
		private String _recordingNow;
		
		public Builder(String companyId,String opId,String seq){
			_companyId = companyId;
			_opId = opId;
			_messageType = ObtainMember.responseType();
			_seq = seq;
		}
		
		public Builder setWorkId(String workId){
			_workId = workId;
			return this;
		}
		
		public Builder setName(String name){
			_name = name;
			return this;
		}
		
		public Builder setGroupAttribute(String groupAttribute){
			_groupAttribute = groupAttribute;
			return this;
		}
		
		public Builder setGroupString(String groupString){
			_groupString = groupString;
			return this;
		}
		
		public Builder setNumber(String number){
			_number = number;
			return this;
		}
		
		public Builder setLoginState(String loginState){
			_loginState = loginState;
			return this;
		}
		
		public Builder setMobileState(String mobileState){
			_mobileState = mobileState;
			return this;
		}
		
		public Builder setState(String state){
			_state = state;
			return this;
		}
		
		public Builder setBindState(String bindState){
			_bindState = bindState;
			return this;
		}
		
		public Builder setCallState(String callState){
			_callState = callState;
			return this;
		}
		
		public Builder setMobileNumber(String mobileNumber){
			_mobileNumber = mobileNumber;
			return this;
		}
		
		public Builder setWorkModel(String workModel){
			_workModel = workModel;
			return this;
		}
		
		public Builder setRecordFlag(String flag){
			_recordFlag = flag;
			return this;
		}
		
		public Builder setRecordingNow(String recordingNow){
			_recordingNow = recordingNow;
			return this;
		}
		
		public ObtainMemberResponse build(){
			ObtainMemberResponse m = new ObtainMemberResponse(
					_companyId,_opId,_messageType,_seq);
			
			m.setBindState(_bindState);
			m.setCallState(_callState);
			m.setGroupAttribute(_groupAttribute);
			m.setGroupString(_groupString);
			m.setLoginState(_loginState);
			m.setMobileNumber(_mobileNumber);
			m.setMobileState(_mobileState);
			m.setName(_name);
			m.setNumber(_number);
			m.setState(_state);
			m.setWorkId(_workId);
			m.setRecordFlag(_recordFlag);
			m.setRecordingNow(_recordingNow);
			m.setWorkModel(_workModel);
			
			return m;
		}
	}
	
	private String _workId;
	private String _name;
	private String _groupAttribute;
	private String _groupString;
	private String _number;
	private String _loginState;
	private String _mobileState;
	private String _state;
	private String _bindState;
	private String _callState;
	private String _mobileNumber;
	private String _workModel;
	private String _recordFlag;
	private String _recordingNow;

	private ObtainMemberResponse(String companyId, 
			String opId, String messageType,String seq) {
		
		super(companyId, opId, messageType,seq);
	}

	public String getWorkId(){
		return _workId;
	}
	
	private void setWorkId(String workId){
		_workId = workId;
	}
	
	public String getName(){
		return _name;
	}
	
	private void setName(String name){
		_name = name;
	}
	
	public String getGroupAttribute(){
		return _groupAttribute;
	}
	
	private void setGroupAttribute(String groupAttribute){
		_groupAttribute = groupAttribute;
	}
	
	public String getGroupString(){
		return _groupString; 
	}
	
	private void setGroupString(String groupString){
		_groupString = groupString;
	}
	
	public String getNumber(){
		return _number;
	}
	
	private void setNumber(String number){
		_number = number;
	}
	
	public String getLoginState() {
		return _loginState;
	}

	private void setLoginState(String loginState) {
		_loginState = loginState;
	}

	
	public String getMobileState() {
		return _mobileState;
	}

	private void setMobileState(String mobileState) {
		_mobileState = mobileState;
	}
	
	public String getState() {
		return _state;
	}

	private void setState(String state) {
		_state = state;
	}
	
	public String getBindState() {
		return _bindState;
	}

	private void setBindState(String bindState) {
		_bindState = bindState;
	}

	public String getCallState() {
		return _callState;
	}

	private void setCallState(String callState) {
		_callState = callState;
	}

	public String getMobileNumber() {
		return _mobileNumber;
	}

	private void setMobileNumber(String mobileNumber) {
		_mobileNumber = mobileNumber;
	}

	public String getRecordFlag(){
		return _recordFlag;
	}
	
	private void setRecordFlag(String recordFlag){
		_recordFlag = recordFlag;
	}
	
	public String getRecordingNow(){
		return _recordingNow;
	}
	
	private void setRecordingNow(String recordingNow){
		_recordingNow = recordingNow;
	}
	
	public String getWorkModel() {
		return _workModel;
	}

	private void setWorkModel(String workModel) {
		_workModel = workModel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ObtainMemberResponse [_workId=");
		builder.append(_workId);
		builder.append(", _name=");
		builder.append(_name);
		builder.append(", _groupAttribute=");
		builder.append(_groupAttribute);
		builder.append(", _groupString=");
		builder.append(_groupString);
		builder.append(", _number=");
		builder.append(_number);
		builder.append(", _loginState=");
		builder.append(_loginState);
		builder.append(", _mobileState=");
		builder.append(_mobileState);
		builder.append(", _state=");
		builder.append(_state);
		builder.append(", _bindState=");
		builder.append(_bindState);
		builder.append(", _callState=");
		builder.append(_callState);
		builder.append(", _mobileNumber=");
		builder.append(_mobileNumber);
		builder.append(", _workModel=");
		builder.append(_workModel);
		builder.append(", _recordFlag=");
		builder.append(_recordFlag);
		builder.append(", _recordingNow=");
		builder.append(_recordingNow);
		builder.append(", _companyId=");
		builder.append(_companyId);
		builder.append(", _opId=");
		builder.append(_opId);
		builder.append(", _messageType=");
		builder.append(_messageType);
		builder.append(", _seq=");
		builder.append(_seq);
		builder.append("]");
		return builder.toString();
	}
}
