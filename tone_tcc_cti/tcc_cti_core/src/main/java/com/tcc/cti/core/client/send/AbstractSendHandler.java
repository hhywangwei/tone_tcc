package com.tcc.cti.core.client.send;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.send.SendMessage;

public abstract class AbstractSendHandler implements SendHandler{
	private static final Logger logger = LoggerFactory.getLogger(AbstractSendHandler.class);
	
	@Override
	public void send(SocketChannel channel,SendMessage message,GeneratorSeq generator,String charset)throws ClientException {
		try {
			if(isSend(message)) {
				byte[] m = getMessage(message, generator, charset);
				ByteBuffer buffer = ByteBuffer.wrap(m);
				channel.write(buffer);	
			}
		} catch (IOException e) {
			logger.error("Tcp client send message is error \"{}\"",e);
			throw new ClientException(e);
		}
	}
	
	/**
	 * 判断发送处理是否可发送该消息
	 * 
	 * @param message 消息对象
	 * @return true:发送
	 */
	protected abstract boolean isSend(SendMessage message);
	
	/**
	 * 得到指定编码的发送信息
	 *
	 * @param message 消息对象
	 * @param generator 消息序号生成对象
	 * @param charset 字符集
	 * @return
	 */
	protected abstract byte[] getMessage(
			SendMessage message,GeneratorSeq generator,String charset);
	
}
