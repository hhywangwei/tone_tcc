package com.tcc.cti.core.client.receive;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.receive.handler.ReceiveHandler;

public class CtiReceive implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(CtiReceive.class);
	
	private static final String DEFAULT_CHARSET = "ISO-8859-1";
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 2;
	
	private final Selector selector;
	private final List<ReceiveHandler> handlers;
	private final String charset;
	private final int bufferSize ;
	private boolean checkCompletion = false;
	
	public CtiReceive(Selector selector,List<ReceiveHandler> handlers){
		this(selector,handlers,DEFAULT_CHARSET,DEFAULT_BUFFER_SIZE);
	}
	
	public CtiReceive(Selector selector,List<ReceiveHandler> handlers,String charset,int bufferSize){
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
					
					ByteBuffer buffer = ByteBuffer.allocate(2048);
					SocketChannel sc =(SocketChannel) sk.channel();
					sc.read(buffer);
					buffer.flip();
					
					String message = Charset.forName(charset).
							newDecoder().decode(buffer).toString();
					logger.debug("Receive message is \"{}\"",message);
					
					//TODO 判读消息是否为空
					
					if(checkCompletion && isCompletion(message)) {
						for(ReceiveHandler handler :handlers){
							handler.receive(message);
						}
					}
					 
				}
			}
		}catch(Exception e){
			
		}
		
	}
	
	private boolean isCompletion(String message){
		//TODO 验证消息是否完整,通过消息长度验证
		return false;
	}
	
	public void setCheckCompletion(boolean check){
		checkCompletion = check;
	}
}
