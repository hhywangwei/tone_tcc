package com.tcc.cti.core.client.send;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 实现消息发送类,接受消息并分解到Map中
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public abstract class AbstractSendHandler implements SendHandler {
	private static final Logger logger = LoggerFactory.getLogger(AbstractSendHandler.class);
	
	private static final String DEFAULT_MESSAGE_LENGTH = "00000";
	private static final String HEAD_FORMAT = "<head>%s</head>";
	
	protected static final String MSG_FORMAT = "<msg>%s</msg>";
	protected static final String SEQ_FORMAT = "<seq>%s</seq>"; 
	protected static final String COMPANY_ID_FORMAT = "<CompanyID>%s</CompanyID>";
	protected static final String OPID_FORMAT = "<OPID>%s</OPID>";

	@Override
	public void send(SocketChannel channel, RequestMessage message, GeneratorSeq generator,
			String charset) throws IOException {
		
		if (isSend(message)) {
			byte[] m = getMessage(message, generator, charset);
			ByteBuffer buffer = ByteBuffer.wrap(m);
			channel.write(buffer);
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
	protected byte[] getMessage(RequestMessage message,GeneratorSeq generator,String charset) {
		
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
