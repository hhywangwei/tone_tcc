package com.tcc.cti.core.client.send;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.RequestMessage;

public abstract class AbstractSendHandler implements SendHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractSendHandler.class);
	private static final String DEFAULT_MESSAGE_LENGTH = "00000";
	private static final String HEAD_FORMAT = "<head>%s</head>";

	@Override
	public void send(SocketChannel channel, RequestMessage message, GeneratorSeq generator,
			String charset) throws ClientException {
		try {
			if (isSend(message)) {
				byte[] m = getMessage(message, generator, charset);
				ByteBuffer buffer = ByteBuffer.wrap(m);
				channel.write(buffer);
			}
		} catch (IOException e) {
			logger.error("Tcp client send message is error \"{}\"", e);
			throw new ClientException(e);
		}
	}

	/**
	 * 判断发送处理是否可发送该消息
	 * 
	 * @param message
	 *            消息对象
	 * @return true:发送
	 */
	protected abstract boolean isSend(RequestMessage message);

	/**
	 * 得到指定编码的发送信息
	 * 
	 * @param message
	 *            消息对象
	 * @param generator
	 *            消息序号生成对象
	 * @param charset
	 *            字符集
	 * @return
	 */
	protected byte[] getMessage(
			RequestMessage message,GeneratorSeq generator,String charset) {
		
		String m = buildMessage(message,generator);
	    return headCompletion(m,charset);
	}

	/**
	 * 构建发送消息
	 * 
	 * @param message 消息
	 * @param generator 创建序列对象
	 * @return
	 */
	protected abstract String buildMessage(RequestMessage message,GeneratorSeq generator);
	
	/**
	 * 替补消息头
	 * 
	 * @param message 消息
	 * @param charset 字符集
	 * @return
	 */
	protected byte[] headCompletion (String message,String charset){
    	Charset c = Charset.forName(charset);
    	byte[] bytes = message.getBytes(c);
    	int length = bytes.length;
    	if(length > MESSAGE_MAX_LENGTH){
    		logger.error("Login message is {},but upper limit {}",
    				length,MESSAGE_MAX_LENGTH);
    		throw new IllegalStateException("Login message is too length");
    	}
    	
    	String m = getHeadSegment(length) + message;
    	return m.getBytes(c);
	}
	
	/**
	 * 得到发送消息头
	 * 
	 * @param length
	 * @return
	 */
	protected String getHeadSegment(int length) {
		String ds = DEFAULT_MESSAGE_LENGTH;
		String rs = String.valueOf(length);
		String ls = ds.substring(0, ds.length() - rs.length()) + rs;
		return String.format(HEAD_FORMAT, ls);
	}

}
