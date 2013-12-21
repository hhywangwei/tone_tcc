package com.tcc.cti.core.message.response;

/**
 * 接收来电结束信息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class CloseCallResponse extends Response{
	
	public static class Builder{
		
		private final String _seq;
		private String _groupId;
		private String _callLeg;
		private String _callerNumber;
		private String _accessNumber;
		private String _calledNumber;
		private String _releaseReason;
		
		public Builder(String seq) {
			_seq = seq;
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
		
		public Builder setReleaseReason(String releaseReason){
			_releaseReason = releaseReason;
			return this;
		}
		
		public CloseCallResponse build(){
			CloseCallResponse r = new CloseCallResponse(_seq);
			
			r.setGroupId(_groupId);
			r.setCallLeg(_callLeg);
			r.setCallerNumber(_callerNumber);
			r.setAccessNumber(_accessNumber);
			r.setCalledNumber(_calledNumber);
			r.setReleaseReason(_releaseReason);
			
			return r;
		}
		
	}

	private String _groupId;
	private String _callLeg;
	private String _callerNumber;
	private String _accessNumber;
	private String _calledNumber;
	private String _releaseReason;
	
	private CloseCallResponse(String seq) {
		super(seq, SUCCESS_RESULT);
	}
	
	private void setGroupId(String groupId){
		_groupId = groupId;
	}
	
	public String getGroupId(){
		return _groupId;
	}
	
	private void setCallLeg(String callLeg){
		_callLeg = callLeg;
	}

	public String getCallLeg(){
		return _callLeg;
	}
	
	private void setCallerNumber(String callerNumber){
		_callerNumber = callerNumber;
	}
	
	public String getCallerNumber(){
		return _callerNumber;
	}
	
	private void setAccessNumber(String accessNumber){
		_accessNumber = accessNumber;
	}
	
	public String getAccessNumber(){
		return _accessNumber;
	}
	
	private void setCalledNumber(String calledNumber){
		_calledNumber = calledNumber;
	}
	
	public String getCalledNumber(){
		return _calledNumber;
	}
	
	private void setReleaseReason(String releaseReason){
		_releaseReason = releaseReason;
	}
	
	public String getReleaseReason(){
		return _releaseReason;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("CloseCallResponse [_groupId=");
		builder2.append(_groupId);
		builder2.append(", _callLeg=");
		builder2.append(_callLeg);
		builder2.append(", _callerNumber=");
		builder2.append(_callerNumber);
		builder2.append(", _accessNumber=");
		builder2.append(_accessNumber);
		builder2.append(", _calledNumber=");
		builder2.append(_calledNumber);
		builder2.append(", _releaseReason=");
		builder2.append(_releaseReason);
		builder2.append(", _seq=");
		builder2.append(_seq);
		builder2.append("]");
		return builder2.toString();
	}
}
