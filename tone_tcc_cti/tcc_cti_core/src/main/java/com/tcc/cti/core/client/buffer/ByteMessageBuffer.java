package com.tcc.cti.core.client.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用{@link ByteBuffer}为容器，实现{@link MessageBuffer}功能
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class ByteMessageBuffer implements MessageBuffer{
	private static final Logger logger = LoggerFactory.getLogger(ByteMessageBuffer.class);
	
	private static final int HEAD_LENGTH = 18;
	private static final int HEAD_VALUE_LENGTH = 5;
	private static final int HEAD_VALUE_OFFSET = 6;
	private static final int MAX_MESSAGE_LENGTH = 32 * 1024;
	
	private static final Pattern DEFAULT_HEAD_PATTERN = Pattern.compile("<head>\\d{5}</head>");
	private static final int DEFAULT_CAPACITY = 2 * 1024 * 1024;
	private static final String DEFAULT_CHARSET_NAME = "ISO-8859-1";
	
	protected final ByteBuffer _buffer;
	private final Charset _charset;
	private final Pattern headPatter = DEFAULT_HEAD_PATTERN;
	
	
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
				if(len > MAX_MESSAGE_LENGTH){
					clearBuffer();
					_buffer.wait();
					continue;
				}
				int remaining = _buffer.reset().remaining();
				if(len == -1 || len > remaining){
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
	
	/**
	 * 清除缓存区
	 */
	private void clearBuffer(){
		_buffer.position(0).limit(0).mark();
	}
	
	/**
	 * 从消息头得到消息长度
	 * 
	 * @return
	 */
	private int getMessageLength(){
		
		int headLength = HEAD_LENGTH;
		int remaining = _buffer.reset().remaining();
		if(remaining < headLength){
			return -1;
		}
		byte[] head = new byte[headLength];
		_buffer.get(head);
		boolean enable = isHead(head);
		if(enable){
			return parseMessageLength(head,headLength);
		}else{
			positionNextHead();
			return getMessageLength();
		}
	}
	
	/**
	 * 判断读取的消息头是否正确
	 * <pre>
	 * 消息头格式：<head>00001</head>
	 * <pre>
	 * @param bytes 读取的消息头
	 * @return
	 */
	private boolean isHead(byte[] bytes){
		String s = new String(bytes,_charset);
		Matcher matcher = headPatter.matcher(s);
		return matcher.matches();
	}
	
	/**
	 * 解析消息头得到消息长度
	 * 
	 * @param bytes 消息头
	 * @param headLength 消息头长度
	 * @return
	 */
	private int parseMessageLength(byte[] bytes,int headLength){
		int len = HEAD_VALUE_LENGTH;
		byte[] lenValues = new byte[len];
		int offset = HEAD_VALUE_OFFSET;
		System.arraycopy(bytes, offset, lenValues, 0, len);
		String l = new String(lenValues,_charset);
		logger.debug("Message len is \"{}\"",l);
		return Integer.valueOf(l) + headLength;
	}
	
	/**
	 * 指定下一个消息头位置
	 */
	private void positionNextHead(){
		int remaining = _buffer.reset().remaining();
		if(remaining > MAX_MESSAGE_LENGTH){
			clearBuffer();
			return;
		}
		byte[] bytes = new byte[remaining];
		String s = new String(bytes,_charset);
		Matcher matcher = headPatter.matcher(s);
		if(matcher.matches()){
			int start = matcher.start();
			int position = getBytesLength(s,0,start) - 1;
			_buffer.position(position).mark();
		}
	}
	
	/**
	 * 通过得字符转换byte数据组长度，得到字符在{@link ByteBuffer}中所占长度。
	 * 
	 * @param s 字符串
	 * @param offset 起始位置
	 * @param len 长度
	 * @return
	 */
	private int getBytesLength(String s,int offset,int len){
		return s.substring(offset, len).getBytes(_charset).length;
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