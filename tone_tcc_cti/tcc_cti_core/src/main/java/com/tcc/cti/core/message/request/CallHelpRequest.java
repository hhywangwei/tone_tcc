package com.tcc.cti.core.message.request;

import static com.tcc.cti.core.message.MessageType.CallHelp;

/**
 * 求助需求对象
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class CallHelpRequest extends RequestMessage{
	private String _callLeg;
	private String _transferWorkId;
	private String _transferNumber;
	private String _status;

	public CallHelpRequest() {
		super(CallHelp.request());
	}
	
	public void setCallLeg(String callLeg){
		_callLeg = callLeg;
	}
	
	public String getCallLeg(){
		return _callLeg;
	}
	
	public void setTransferWorkId(String transferWorkID){
		_transferWorkId = transferWorkID;
	}
	
	public String getTransferWorkId(){
		return _transferWorkId;
	}
	
	public void setTransferNumber(String number){
		_transferNumber = number;
	}
	
	public String getTransferNumber(){
		return _transferNumber;
	}
	
	public void setStatus(String status){
		_status = status;
	}
	
	public String getStatus(){
		return _status;
	}

	@Override
	public String toString() {
		return "CallHelpRequest [_callLeg=" + _callLeg + ", _transferWorkID="
				+ _transferWorkId + ", _transferNumber=" + _transferNumber
				+ ", _status=" + _status + ", _messageType=" + _messageType
				+ "]";
	}
}
