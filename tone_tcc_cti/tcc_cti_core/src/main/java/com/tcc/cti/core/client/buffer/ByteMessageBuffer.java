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
	private static final int MESSAGE_MAX_LENGTH = 32 * 1024;
	private static final int MISS_MESSAGE_MAX_LENGTH = 8 * 1024;
	
	private static final int DEFAULT_CAPACITY = 256 * 1024;
	private static final Pattern DEFAULT_HEAD_PATTERN = Pattern.compile("<head>\\d{5}</head>");
	
	
	private final int _maxLength; 
	private final Charset _charset;
	private final ByteBuffer _buffer;
	
	private final Pattern headPatter = DEFAULT_HEAD_PATTERN;
	
	
	/**
	 * 实例{@link ByteMessageBuffer}对象
	 */
	public ByteMessageBuffer(String charset){
		this(DEFAULT_CAPACITY,charset);
	}
	
	/**
	 * 实例{@link ByteMessageBuffer}对象
	 * 
	 * @param capacity 缓冲区大小
	 * @param charsetName 消息字符集
	 */
	public ByteMessageBuffer(int capacity,String charset){
		_maxLength = (capacity < MESSAGE_MAX_LENGTH)? capacity: MESSAGE_MAX_LENGTH;
		_charset = Charset.forName(charset);
		_buffer =(ByteBuffer)ByteBuffer.
				allocate(capacity).
				position(0).
				limit(0).
				mark();
	}

	@Override
	public String next() {
		synchronized (_buffer) {
			int remaining = _buffer.reset().remaining();
			
			if(remaining ==  0){
				logger.debug("message is empty");
				return null;
			}
			
			int len =  getMessageLength(remaining);
			if(len > _maxLength){
				logger.debug("message {} too length",len);
				clearBuffer();
				return null;
			}
			
			if(notComplete(len,remaining)){
				logger.debug("message is not complete");
				return null;
			}
			
			_buffer.reset();
			byte[] m = new byte[len];
			_buffer.get(m);
			_buffer.mark();
			
			String message = new String(m,_charset);
			logger.debug("Message is \"{}\"",message);
			return message;
		}
	}
	
	/**
	 * 消息不完整
	 * 
	 * @param len 消息长度
	 * @param remaining buff中真实消息长度
	 * @return
	 */
	private boolean notComplete(int len,int remaining){
		return len == -1 || len > remaining;
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
	private int getMessageLength(int remaining){		
		int headLength = HEAD_LENGTH;
		if(remaining < headLength){
			return -1;
		}
		byte[] head = new byte[headLength];
		logger.debug("message head is {}",new String(head));
		_buffer.get(head);
		boolean enable = isHead(head);
		if(enable){
			return parseMessageLength(head,headLength);
		}else{
			boolean hasNext = positionNextHead();
			return hasNext ? getMessageLength(remaining) : -1;
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
	private boolean positionNextHead(){
		int remaining = _buffer.reset().remaining();
		byte[] bytes = new byte[remaining];
		_buffer.get(bytes);
		String s = new String(bytes,_charset);
		Matcher matcher = headPatter.matcher(s);
		boolean hasNext = false;
		if(matcher.find()){
			int start = matcher.start();
			int position = getBytesLength(s,0,start) ;
			_buffer.position(position).mark();
			hasNext = true;
		}else{
			if(remaining > MISS_MESSAGE_MAX_LENGTH){
				clearBuffer();
			}			
		}
		return hasNext;
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
	public void append(byte[] bytes) {
		
		if(bytes == null || bytes.length == 0){
			logger.debug("Message is empty");
			return ;
		}
		
		if(bytes.length > _maxLength){
			logger.warn("Append message \"{}\" is too length",new String(bytes,_charset));
			return ;
		}
		
		synchronized (_buffer) {
			int len = bytes.length;
			int free= _buffer.capacity() - _buffer.limit() ;
			
			if(free < len ){
				int position = _buffer.reset().position();
				if((free + position) > len){
					_buffer.compact();
					_buffer.flip().mark();
				}else{
					logger.warn("Message buffer is full");
					clearBuffer();
				}
			}
			
			int limit = _buffer.limit();
			_buffer.position(limit).limit(limit + len);
			String m = new String(bytes,_charset);
			logger.debug("Append message is \"{}\"", m);
			_buffer.put(bytes);
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