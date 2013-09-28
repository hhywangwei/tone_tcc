package com.tcc.cti.core.message.response;

import static com.tcc.cti.core.message.MessageType.Monitor;

/**
 * 获得班长信息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class MonitorResponse extends ResponseMessage{

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
		
		public Builder(String companyId,String opId,String seq){
			_companyId = companyId;
			_opId = opId;
			_messageType = Monitor.response();
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
		
		public MonitorResponse build(){
			MonitorResponse m = new MonitorResponse(
					_companyId,_opId,_messageType,_seq);
			
			m.setBindState(_bindState);
			m.setCallState(_callState);
			m.setGroupAttribute(_groupAttribute);
			m.setGroupString(_groupString);
			m.setLoginState(_loginState);
			m.setMobileState(_mobileState);
			m.setName(_name);
			m.setNumber(_number);
			m.setState(_state);
			m.setWorkId(_workId);
			
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

	public MonitorResponse(String companyId, String opId,
			String messageType,String seq) {
		super(companyId, opId, messageType, seq);
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

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("MonitorResponse [_workId=");
		builder2.append(_workId);
		builder2.append(", _name=");
		builder2.append(_name);
		builder2.append(", _groupAttribute=");
		builder2.append(_groupAttribute);
		builder2.append(", _groupString=");
		builder2.append(_groupString);
		builder2.append(", _number=");
		builder2.append(_number);
		builder2.append(", _loginState=");
		builder2.append(_loginState);
		builder2.append(", _mobileState=");
		builder2.append(_mobileState);
		builder2.append(", _state=");
		builder2.append(_state);
		builder2.append(", _bindState=");
		builder2.append(_bindState);
		builder2.append(", _callState=");
		builder2.append(_callState);
		builder2.append(", _companyId=");
		builder2.append(_companyId);
		builder2.append(", _opId=");
		builder2.append(_opId);
		builder2.append(", _messageType=");
		builder2.append(_messageType);
		builder2.append(", _seq=");
		builder2.append(_seq);
		builder2.append("]");
		return builder2.toString();
	}
	
}
