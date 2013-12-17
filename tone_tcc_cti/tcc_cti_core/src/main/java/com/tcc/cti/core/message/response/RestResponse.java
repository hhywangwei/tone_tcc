package com.tcc.cti.core.message.response;

import com.tcc.cti.core.message.MessageType;

/**
 * 请求休息返回信息
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class RestResponse extends ResponseMessage{

	private final String _result;
	
	public RestResponse(String companyId, String opId, 
			String seq,String result) {
		
		super(companyId, opId,MessageType.Rest.response(), seq);
		_result = result;
	}
	
	public String getResult(){
		return _result;
	}

	@Override
	public String toString() {
		return "RestResponse [_result=" + _result + ", _companyId="
				+ _companyId + ", _opId=" + _opId + ", _messageType="
				+ _messageType + ", _seq=" + _seq + "]";
	}
}
