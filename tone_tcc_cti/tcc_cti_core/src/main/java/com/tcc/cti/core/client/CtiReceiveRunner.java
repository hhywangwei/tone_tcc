package com.tcc.cti.core.client;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.receive.ReceiveHandler;

public class CtiReceiveRunner implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(CtiReceiveRunner.class);
	
	private static final String DEFAULT_CHARSET = "ISO-8859-1";
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 2;
	private static final int HEAD_LENGTH = 18;
	
	private final Selector selector;
	private final List<ReceiveHandler> handlers;
	private final String charset;
	private final int bufferSize ;
	private boolean checkCompletion = false;
	
	public CtiReceiveRunner(Selector selector,List<ReceiveHandler> handlers){
		this(selector,handlers,DEFAULT_CHARSET,DEFAULT_BUFFER_SIZE);
	}
	
	public CtiReceiveRunner(Selector selector,List<ReceiveHandler> handlers,String charset,int bufferSize){
		this.selector = selector;
		this.handlers = handlers;
		this.charset = charset;
		this.bufferSize = bufferSize;
	}

	public void run() {
		try{
			while(selector.select() > 0){
				for(SelectionKey sk : selector.selectedKeys()){
					if(!sk.isReadable()) continue;
					
					ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
					SocketChannel sc =(SocketChannel) sk.channel();
					sc.read(buffer);
					buffer.flip();
					
					String message = Charset.forName(charset).
							newDecoder().decode(buffer).toString();
					logger.debug("Receive message is \"{}\"",message);
					
					if(StringUtils.isBlank(message)) continue;
					
					if(checkCompletion && isCompletion(message)) {
						for(ReceiveHandler handler :handlers){
							handler.receive(message);
						}
					}
				}
			}
		}catch(Exception e){
			//TODO 可能需要更多异常处理
			logger.error("Recevice message is error {}",e);
		}
		
	}
	
	private boolean isCompletion(String message){
		if(message.length() < HEAD_LENGTH) return false;
		String l = message.substring(5,10);
		logger.debug("Message length is {}",l);
		int mlength = Integer.valueOf(l) + HEAD_LENGTH;
		return mlength == message.length();
	}
	
	public void setCheckCompletion(boolean check){
		checkCompletion = check;
	}
}
