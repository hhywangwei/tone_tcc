package com.tcc.cti.core.client.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteMessageBuffer implements MessageBuffer{
	private static final Logger logger = LoggerFactory.getLogger(ByteMessageBuffer.class);
	
	private static final int DEFAULT_CAPACITY = 10 * 1024 * 1024;
	private static final int DEFULT_COMPACTS_RATIO = 50;
	private static final String DEFAULT_CHARSET_NAME = "ISO-8859-1";
	private final ByteBuffer _buffer;
	private final int _compactsLimit;
	private final Charset _charset;
	
	/**
	 * 实例{@link ByteMessageBuffer}对象
	 */
	public ByteMessageBuffer(){
		this(DEFAULT_CAPACITY,DEFULT_COMPACTS_RATIO,DEFAULT_CHARSET_NAME);
	}
	
	/**
	 * 实例{@link ByteMessageBuffer}对象
	 * 
	 * @param capacity 缓冲区大小
	 * @param compactsRatio 启动压缩比率（占缓存区比率），如缓存区为1M，position>512K启动压缩过程
	 * @param 消息字符集
	 */
	public ByteMessageBuffer(int capacity,int compactsRatio,String charsetName){
		_buffer = ByteBuffer.allocate(capacity);
		_compactsLimit = (capacity * compactsRatio) / 100;
		_buffer.flip();
		_charset = Charset.forName(charsetName);
	}

	@Override
	public String getNext() throws InterruptedException{
		synchronized (_buffer) {
			_buffer.mark();
			int len = 0;
			while(true){
				len = getMessageLength();
				_buffer.reset();
				if(len == -1 || len > _buffer.remaining()){
					_buffer.wait();
				}else{
					break;
				}
			}
			byte[] m = new byte[len];
			_buffer.get(m);
			compact();
			_buffer.notifyAll();
			
			String message = new String(m,_charset);
			logger.debug("Message is \"{}\"",message);
			return message;
		}
	}
	
	private void compact(){
		if(_buffer.remaining() == 0 ||
				_buffer.limit() > _compactsLimit){
			_buffer.compact();
		}
	}
	
	private int getMessageLength(){
		
		int headLength = 18;
		if(_buffer.remaining() < headLength){
			return -1;
		}
		byte[] head = new byte[headLength];
		_buffer.get(head);
		int headValueLength = 5;
		byte[] len = new byte[headValueLength];
		int headValueOffset = 6;
		System.arraycopy(head, headValueOffset, len, 0, headValueOffset);
		
		String l = new String(len,_charset);
		logger.debug("Message len is \"{}\"",l);
		return Integer.valueOf(l) + headLength;
	}

	@Override
	public void append(byte[] bytes) throws InterruptedException {
		synchronized (_buffer) {
			while(true){
				int free= _buffer.capacity() - _buffer.limit() ;
				if(free < bytes.length){
					_buffer.wait();
				}else{
					break;
				}
			}
			_buffer.put(bytes);
			_buffer.notifyAll();
		}
	}

}