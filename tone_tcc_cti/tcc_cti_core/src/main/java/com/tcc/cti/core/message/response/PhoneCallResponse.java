package com.tcc.cti.core.message.response;

import static com.tcc.cti.core.message.MessageType.PhoneCall;

/**
 * 接受呼叫信息对象
 * 
 * <pre>
 * groupId:分组ID
 * callLeg:呼叫唯一标识
 * callerNumber:主叫电话号码
 * accessNumber:接入号
 * calledNumber:被叫电话号码
 * callState:来电状态 1－排队中 2－振铃 3－接通 4－主叫在保持状态 5－发起呼叫 6－离开排队队列
 * opNumber:座席号
 * name:座席姓名
 * glibalCallLeg:用于关联CDR表、录音表、文本交互的唯一标识
 * recordFlag:录音标识	1－录音，0－没有录音
 * callType:呼叫类型 0 – 客户打入电话 1 – 求助过来的呼叫 2 – 转接到组过来的呼叫 3 – 转接到人过来的呼叫 4 – 座席呼叫内部分机 5 – 座席外呼
 * preOpId:前座席编号
 * preOpNumnber:前座席号
 * preOpName:前座席名
 * userInput:用户按键信息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class PhoneCallResponse extends ResponseMessage{ 
	
	public static class Builder{
		private final String _companyId;
		private final String _messageType;
		private final String _opId;
		private final String _seq;
		private String _groupId;
		private String _callLeg;
		private String _callerNumber;
		private String _accessNumber;
		private String _calledNumber;
		private String _callState;
		private String _opNumber;
		private String _name;
		private String _globalCallLeg;
		private String _recordFlag;
		private String _callType;
		private String _preOpId;
		private String _preOpNumber;
		private String _preOpName;
		private String _userInput;
		
		public Builder(String companyId,String opId,String seq){
			_companyId = companyId;
			_opId = opId;
			_seq = seq;
			_messageType = PhoneCall.response(); 
		}
		
		public Builder setGroupId(String groupId){
			_groupId = groupId;
			return this;
		}
		
		public Builder setCallLeg(String callLeg){
			_callLeg = callLeg;
			return this;
		}
		
		public Builder setCallerNumber(String callerNumber){
			_callerNumber = callerNumber;
			return this;
		}
		
		public Builder setAccessNumber(String accessNumber){
			_accessNumber = accessNumber;
			return this;
		}
		
		public Builder setCalledNumber(String calledNumber){
			_calledNumber = calledNumber;
			return this;
		}
		
		public Builder setCallState(String callState){
			_callState = callState;
			return this;
		}
		
		public Builder setOpNumber(String opNumber){
			_opNumber = opNumber;
			return this;
		}
		
		public Builder setName(String name){
			_name = name;
			return this;
		}
		
		public Builder setGlobalCallLeg(String globalCallLeg){
			_globalCallLeg = globalCallLeg;
			return this;
		}
		
		public Builder setRecordFlag(String recordFlag){
			_recordFlag = recordFlag;
			return this;
		}
		
		public Builder setCallType(String callType){
			_callType = callType;
			return this;
		}
		
		public Builder setPreOpId(String preOpId){
			_preOpId = preOpId;
			return this;
		}
		
		public Builder setPreOpNumber(String preOpNumber){
			_preOpNumber = preOpNumber;
			return this;
		}
		
		public Builder setPreOpName(String preOpName){
			_preOpName = preOpName;
			return this;
		}
		
		public Builder setUserInput(String userInput){
			_userInput = userInput;
			return this;
		}
		
		public PhoneCallResponse build(){
			PhoneCallResponse response = new  PhoneCallResponse(
					_companyId,_opId,_messageType,_seq);
			
			response.setGroupId(_groupId);
			response.setCallLeg(_callLeg);
			response.setCallerNumber(_callerNumber);
			response.setAccessNumber(_accessNumber);
			response.setCalledNumber(_calledNumber);
			response.setCallState(_callState);
			response.setOpNumber(_opNumber);
			response.setName(_name);
			response.setGlobalCallLeg(_globalCallLeg);
			response.setRecordFlag(_recordFlag);
			response.setCallType(_callType);
			response.setPreOpId(_preOpId);
			response.setPreOpNumber(_preOpNumber);
			response.setPreOpName(_preOpName);
			response.setUserInput(_userInput);
			
			return response;
		}
	}

	private PhoneCallResponse(String companyId, String opId,
			String messageType,String seq) {
		
		super(companyId, opId, messageType, seq);
	}
	
	private String _groupId;
	private String _callLeg;
	private String _callerNumber;
	private String _accessNumber;
	private String _calledNumber;
	private String _callState;
	private String _opNumber;
	private String _name;
	private String _globalCallLeg;
	private String _recordFlag;
	private String _callType;
	private String _preOpId;
	private String _preOpNumber;
	private String _preOpName;
	private String _userInput;
	
	public String getGroupId(){
		return _groupId;
	}
	
	private void setGroupId(String groupId){
		_groupId = groupId;
	}

	public String getCallLeg(){
		return _callLeg;
	}
	
	private void setCallLeg(String callLeg){
		_callLeg = callLeg;
	}
	
	public String getCallerNumber(){
		return _callerNumber;
	}
	
	private void setCallerNumber(String callerNumber){
		_callerNumber = callerNumber;
	}
	
	public String getAccessNumber(){
		return _accessNumber;
	}
	
	private void setAccessNumber(String accessNumber){
		_accessNumber = accessNumber;
	}
	
	public String getCalledNumber(){
		return _calledNumber;
	}
	
	private void setCalledNumber(String calledNumber){
		_calledNumber = calledNumber;
	}
	
	public String getCallState(){
		return _callState;
	}
	
	private void setCallState(String callState){
		_callState = callState;
	}
	
	public String getOpNumber(){
		return _opNumber;
	}
	
	private void setOpNumber(String opNumber){
		_opNumber = opNumber;
	}
	
	public String getName(){
		return _name;
	}
	
	private void setName(String name){
		_name = name;
	}
	
	public String getGlobalCallLeg(){
		return _globalCallLeg;
	}
	
	private void setGlobalCallLeg(String globalCallLeg){
		_globalCallLeg = globalCallLeg;
	}
	
	public String getRecordFlag(){
		return _recordFlag;
	}
	
	private void setRecordFlag(String recordFlag){
		_recordFlag = recordFlag;
	}
	
	public String getCallType(){
		return _callType;
	}
	
	private void setCallType(String callType){
		_callType = callType;
	}
	
	public String getPreOpId(){
		return _preOpId;
	}
	
	private void setPreOpId(String preOpId){
		_preOpId = preOpId;
	}
	
	public String getPreOpNumber(){
		return _preOpNumber;
	}
	
	private void setPreOpNumber(String preOpNumber){
		_preOpNumber = preOpNumber;
	}
	
	public String getPreOpName(){
		return _preOpName;
	}
	
	private void setPreOpName(String preOpName){
		_preOpName = preOpName;
	}
	
	public String getUserInput(){
		return _userInput;
	}
	
	private void setUserInput(String userInput){
		_userInput = userInput;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("PhoneCallResponse [_groupId=");
		builder2.append(_groupId);
		builder2.append(", _callLeg=");
		builder2.append(_callLeg);
		builder2.append(", _callNumber=");
		builder2.append(_callerNumber);
		builder2.append(", _accessNumber=");
		builder2.append(_accessNumber);
		builder2.append(", _calledNumber=");
		builder2.append(_calledNumber);
		builder2.append(", _callState=");
		builder2.append(_callState);
		builder2.append(", _opNumber=");
		builder2.append(_opNumber);
		builder2.append(", _name=");
		builder2.append(_name);
		builder2.append(", _globalCallLeg=");
		builder2.append(_globalCallLeg);
		builder2.append(", _recordFlag=");
		builder2.append(_recordFlag);
		builder2.append(", _callType=");
		builder2.append(_callType);
		builder2.append(", _preOpId=");
		builder2.append(_preOpId);
		builder2.append(", _preOpNumber=");
		builder2.append(_preOpNumber);
		builder2.append(", _preOpName=");
		builder2.append(_preOpName);
		builder2.append(", _userInput=");
		builder2.append(_userInput);
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

