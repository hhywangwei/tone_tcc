package com.tcc.cti.core.client.session.process.handler.send;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.client.session.process.handler.SendHandlerable;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

/**
 * 实现消息发送类,接受消息并分解到Map中
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public abstract class AbstractSendHandler implements SendHandlerable {
	private static final Logger logger = LoggerFactory.getLogger(AbstractSendHandler.class);
	private static final int DEFAULT_SIZE = 256;
	
	private static final String DEFAULT_MESSAGE_LENGTH = "00000";
	private static final String HEAD_FORMAT = "<head>%s</head>";
	protected static final String MSG_FORMAT = "<msg>%s</msg>";
	protected static final String SEQ_FORMAT = "<seq>%s</seq>"; 
	protected static final String COMPANY_ID_FORMAT = "<CompanyID>%s</CompanyID>";
	protected static final String OPID_FORMAT = "<OPID>%s</OPID>";

	@Override
	public void send(Sessionable session, Requestable<? extends Response> request,
			GeneratorSeq generator,	Charset charset) throws IOException {
		
		if (request != null && isSend(request)) {
			try{
				OperatorKey key = session.getOperatorKey();
				String seq = generator.next();
				byte[] m = getMessage(request, key, seq, charset);
				ByteBuffer buffer = ByteBuffer.wrap(m);
				SocketChannel channel = session.getSocketChannel();
				request.notifySend(seq);
				channel.write(buffer);	
			}catch(IOException e){
				request.notifySendError(e);
				throw e;
			}
		}
	}

	/**
	 * 判断发送处理是否可发送该消息
	 * 
	 * @param request
	 *            消息对象
	 * @return true 发送
	 */
	protected abstract boolean isSend(Requestable<? extends Response> request);

	/**
	 * 得到指定编码的发送信息
	 * 
	 * @param request
	 *            消息对象
	 * @param key {@link OperatorKey}
	 *            操作员Key
	 * @param generator
	 *            消息序号生成对象
	 * @param charset
	 *            字符集
	 * @return
	 */
	protected byte[] getMessage(Requestable<? extends Response> request,
			OperatorKey key, String seq, Charset charset) {
		
		StringBuilder builder = new StringBuilder(DEFAULT_SIZE);
		buildHead(request.getMessageType(),seq,builder);
		buildMessage(request, key, builder);
		String m = builder.toString();
		logger.debug("Send command is {}", m);
		
	    return headCompletion(m,charset);
	}

	/**
	 * 构建发送信息头
	 * 
	 * @param messageType 消息类型
	 * @param seq         消息序号
	 * @param builder     创建发送消息命令
	 */
	protected void buildHead(String messageType,String seq,StringBuilder builder){
		builder.append(String.format(MSG_FORMAT, messageType));
		builder.append(String.format(SEQ_FORMAT, seq));
	}
	/**
	 * 构建发送消息
	 * 
	 * @param request 消息对象
	 * @param key {@link OperatorKey} 操作员Key
	 * @param builder 创建发送消息命令
	 */
	protected abstract void buildMessage(Requestable<? extends Response> request, OperatorKey key, StringBuilder builder);
	
	/**
	 * 构建操作员信息
	 * 
	 * @param key     操作员
	 * @param builder 创建发送消息命令
	 * @return
	 */
	protected void buildOperator(OperatorKey key,StringBuilder builder){
		builder.append(String.format(COMPANY_ID_FORMAT,key.getCompanyId()));
		builder.append(String.format(OPID_FORMAT, key.getOpId()));
	}
	
	/**
	 * 替补消息头
	 * 
	 * @param message 消息
	 * @param charset 字符集
	 * @return
	 */
	protected byte[] headCompletion (String message,Charset charset){
    	byte[] bytes = message.getBytes(charset);
    	int length = bytes.length;
    	if(length > MESSAGE_MAX_LENGTH){
    		logger.error("Login message is {},but upper limit {}",
    				length,MESSAGE_MAX_LENGTH);
    		throw new IllegalStateException("Login message is too length");
    	}
    	
    	String m = getHeadSegment(length) + message;
    	return m.getBytes(charset);
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
