package com.tcc.cti.driver.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.driver.message.response.CallResponse;

public class Phone {
	private static final Logger logger = LoggerFactory.getLogger(Phone.class);
	
	private String _callLeg;
	private String _callerNumber;
	private String _accessNumber;
	private String _calledNumber;
	private String _globalCallLeg;
	private String _userInput;
	private boolean _calling = false;
	private long _callingTime;
	
	public void calling(CallResponse response){
		_callLeg = response.getCallLeg();
		_callerNumber = response.getCallerNumber();
		_accessNumber = response.getAccessNumber();
		_calledNumber = response.getCalledNumber();
		_globalCallLeg = response.getGlobalCallLeg();
		_userInput = response.getUserInput();
		_calling = true;
		_callingTime = System.currentTimeMillis();
		logger.debug("{}.callLeg'{} start calling",
				System.currentTimeMillis(),_callLeg);
	}
	
	public void closeCall(String callLeg){
		if(_callLeg.equals(callLeg)){
			
			long now = System.currentTimeMillis();
			long deff = (now - _callingTime)/1000;
			logger.debug("{}.call time {},callLeg's {} phone close",
					new Object[]{now,deff,_callLeg});
			
			_callLeg = null;
			_callerNumber = null;
			_accessNumber = null;
			_calledNumber = null;
			_globalCallLeg = null;
			_userInput = null;
			_calling = false;
			_callingTime = 0L;
		}
	}
	
	public String getCallLeg(){
		return _callLeg;
	}
	
	public String getCallerNumber(){
		return _callerNumber;
	}
	
	public String getAccessNumber(){
		return _accessNumber;
	}
	
	public String getCalledNumber(){
		return _calledNumber;
	}
	
	
	public void setGlobalCallLeg(String globalCallLeg){
		_globalCallLeg = globalCallLeg;
	}
	
	public String getGlobalCallLeg(){
		return _globalCallLeg;
	}
	
	public void setUserInput(String userInput){
		_userInput = userInput;
	}
	
	public String getUserInput(){
		return _userInput;
	}

	public boolean isCalling(){
		return _calling;
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
		builder.append(", _globalCallLeg=");
		builder.append(_globalCallLeg);
		builder.append(", _userInput=");
		builder.append(_userInput);
		builder.append(", _calling=");
		builder.append(_calling);
		builder.append("]");
		return builder.toString();
	}
}
