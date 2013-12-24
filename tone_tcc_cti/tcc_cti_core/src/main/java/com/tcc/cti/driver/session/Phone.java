package com.tcc.cti.driver.session;

public class Phone {
	
	public enum CallState{
		None,Queue,Bell,Connect,Calling,StartCall,OutQueue;
	}
	
	public enum CallType{
		InCall,HelpCall,TransferGroup,TransferOne,CallInter,CallOut;
	}
	
	private String _callLeg;
	private String _callerNumber;
	private String _accessNumber;
	private String _calledNumber;
	private CallState _callState;
	private String _globalCallLeg;
	private CallType _callType;
	private String _userInput;
	
	public void setCallLeg(String callLeg){
		_callLeg = callLeg;
	}
	
	public String getCallLeg(){
		return _callLeg;
	}
	
	public void setCallerNumber(String callerNumber){
		_callerNumber =  callerNumber;
	}
	
	public String getCallerNumber(){
		return _callerNumber;
	}
	
	public void setAccessNumber(String accessNumber){
		_accessNumber = accessNumber;
	}
	
	public String getAccessNumber(){
		return _accessNumber;
	}
	
	public void setCalledNumber(String calledNumber){
		_calledNumber = calledNumber;
	}
	
	public String getCalledNumber(){
		return _calledNumber;
	}
	
	public void setCallState(CallState callState){
		_callState =  callState;
	}
	
	public CallState getCallState(){
		return _callState;
	}
	
	public void setGlobalCallLeg(String globalCallLeg){
		_globalCallLeg = globalCallLeg;
	}
	
	public String getGlobalCallLeg(){
		return _globalCallLeg;
	}
	
	public void setCallType(CallType callType){
		_callType = callType;
	}
	
	public CallType getCallType(){
		return _callType;
	}
	
	public void setUserInput(String userInput){
		_userInput = userInput;
	}
	
	public String getUserInput(){
		return _userInput;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Phone [_callLeg=");
		builder.append(_callLeg);
		builder.append(", _callerNumber=");
		builder.append(_callerNumber);
		builder.append(", _accessNumber=");
		builder.append(_accessNumber);
		builder.append(", _calledNumber=");
		builder.append(_calledNumber);
		builder.append(", _callState=");
		builder.append(_callState);
		builder.append(", _globalCallLeg=");
		builder.append(_globalCallLeg);
		builder.append(", _callType=");
		builder.append(_callType);
		builder.append(", _userInput=");
		builder.append(_userInput);
		builder.append("]");
		return builder.toString();
	}
	
}
