package org.tcc.cti.core.message;

/**
 * 根据cti服务要求,生成cti服务请求消息接口。
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface CtiMessageable {
	
	/**
	 * 得到发送的消息，
	 * 
	 * @return
	 */
	String getMessage();
	
	/**
	 * 得到指定编码的发送信息
	 * 
	 * @param charset 字符集
	 * @return
	 */
	byte[] getMessage(String charset);
	
	/**
	 * 得到发送消息，消息被转换成ISO-8859-1编码byte数组
	 * 
	 * @return
	 */
	byte[] getMessageISO();

}
