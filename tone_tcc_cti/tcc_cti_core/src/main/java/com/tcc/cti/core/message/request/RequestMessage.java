package com.tcc.cti.core.message.request;

/**
 * 发送消息
 * 
 * <pre>
 * _messageType:消息类型
 * </pre>
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 *
 */
public class RequestMessage {

	protected final String _messageType;
	
	public RequestMessage(String messageType){
		_messageType = messageType;
	}
	 
	public String getMessageType(){
		return _messageType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestMessage [_messageType=");
		builder.append(_messageType);
		builder.append("]");
		return builder.toString();
	}
}
