package com.tcc.cti.core.client.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过{@link ByteBuffer}实现{@link MessageBuffer}
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class ByteMessageBuffer implements MessageBuffer{
	private static final Logger logger = LoggerFactory.getLogger(ByteMessageBuffer.class);
	
	private static final int DEFAULT_CAPACITY = 2 * 1024 * 1024;
	private static final String DEFAULT_CHARSET_NAME = "ISO-8859-1";
	protected final ByteBuffer _buffer;
	private final Charset _charset;
	
	/**
	 * 实例{@link ByteMessageBuffer}对象
	 */
	public ByteMessageBuffer(){
		this(DEFAULT_CAPACITY,DEFAULT_CHARSET_NAME);
	}
	
	/**
	 * 实例{@link ByteMessageBuffer}对象
	 * 
	 * @param capacity 缓存区大小
	 */
	public ByteMessageBuffer(int capacity){
		this(capacity,DEFAULT_CHARSET_NAME);
	}
	
	/**
	 * 实例{@link ByteMessageBuffer}对象
	 * 
	 * @param capacity 缓冲区大小
	 * @param 消息字符集
	 */
	public ByteMessageBuffer(int capacity,String charsetName){
		_charset = Charset.forName(charsetName);
		_buffer =(ByteBuffer)ByteBuffer.
				allocate(capacity).
				position(0).
				limit(0).
				mark();
	}

	@Override
	public String next() throws InterruptedException{
		synchronized (_buffer) {
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
			_buffer.mark();
			_buffer.notifyAll();
			
			String message = new String(m,_charset);
			logger.debug("Message is \"{}\"",message);
			return message;
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
		byte[] lenValues = new byte[headValueLength];
		int headValueOffset = 6;
		System.arraycopy(head, headValueOffset, lenValues, 0, headValueLength);
		
		String l = new String(lenValues,_charset);
		logger.debug("Message len is \"{}\"",l);
		return Integer.valueOf(l) + headLength;
	}

	@Override
	public void append(byte[] bytes) throws InterruptedException {
		synchronized (_buffer) {
			int len = bytes.length;
			while(true){
				int free= _buffer.capacity() - _buffer.limit() ;
				if(free < len ){
					if((free + _buffer.position()) > len){
						_buffer.compact();
						_buffer.flip().mark();
						break;
					}
					_buffer.wait();
				}else{
					break;
				}
			}
			
			int limit = _buffer.limit();
			_buffer.position(limit).limit(limit + len);
			_buffer.put(bytes);
			_buffer.reset();
			_buffer.notifyAll();
		}
	}
	
	protected int getPosition(){
		synchronized (_buffer) {
			return _buffer.position();
		}
	}
	
	protected int getLimit(){
		synchronized (_buffer) {
			return _buffer.limit();
		}
	}
}