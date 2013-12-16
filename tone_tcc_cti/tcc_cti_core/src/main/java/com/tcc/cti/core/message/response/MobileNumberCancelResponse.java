package com.tcc.cti.core.message.response;

import com.tcc.cti.core.message.MessageType;

/**
 * 接收取消移动设置信息对象
 * 
 * <pre>
 * _result:返回设置移动结果{@value 0}成功
 * </per>
 * 
 * @author WangWei
 *
 */
public class MobileNumberCancelResponse extends ResponseMessage {
	private String _result;
	
	public MobileNumberCancelResponse(String companyId, String opId,
			String seq,String result) {
		super(companyId, opId, MessageType.MobileNumberCancel.response(), seq);
		_result = result;
	}

	public String get_result() {
		return _result;
	}

	public void set_result(String _result) {
		this._result = _result;
	}

	@Override
	public String toString() {
		return "MobileNumberCancelResponse [_result=" + _result
				+ ", _companyId=" + _companyId + ", _opId=" + _opId
				+ ", _messageType=" + _messageType + ", _seq=" + _seq + "]";
	}
}
