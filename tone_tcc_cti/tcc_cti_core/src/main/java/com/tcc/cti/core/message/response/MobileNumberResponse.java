package com.tcc.cti.core.message.response;

import com.tcc.cti.core.message.MessageType;

/**
 * 接收移动设置信息对象
 * 
 * <pre>
 * _result:返回设置移动结果{@value 0}成功
 * </per>
 * 
 * @author WangWei
 *
 */
public class MobileNumberResponse extends ResponseMessage{
	private final String _result;
	
	public MobileNumberResponse(String companyId, String opId, 
			String seq,String result) {
		super(companyId, opId, MessageType.MobileNumber.response(), seq);
		_result = result;
	}
	
	public String getResult(){
		return _result;
	}

	@Override
	public String toString() {
		return "MobileNumberResponse [_result=" + _result + ", _companyId="
				+ _companyId + ", _opId=" + _opId + ", _messageType="
				+ _messageType + ", _seq=" + _seq + "]";
	}
}
