package com.tcc.cti.core.client.buffer;

import org.junit.Assert;
import org.junit.Test;

/**
 * 单元测试{@link ByteMessageBuffer}
 * 
 * @author <a href="hhywangwei@gmail.com">wangewei</a>
 */
public class ByteMessageBufferTest {
	private final String charset = "ISO-8859-1";
	
	@Test
	public void testAppend(){
		ByteMessageBuffer buffer = new ByteMessageBuffer(100,charset);
		byte[] bytes = "abcd123456".getBytes();
		buffer.append(bytes);
		Assert.assertEquals(10, buffer.getPosition());
		Assert.assertEquals(10, buffer.getLimit());
		
		bytes = "efg".getBytes();
		buffer.append(bytes);
		Assert.assertEquals(13, buffer.getPosition());
		Assert.assertEquals(13, buffer.getLimit());
	}
	
	@Test
	public void testNext(){
		String mes = "<head>00005</head>12345<head>";
		ByteMessageBuffer buffer = new ByteMessageBuffer(100,charset);
		buffer.append(mes.getBytes());
		
		String m = buffer.next();
		Assert.assertEquals("<head>00005</head>12345", m);
		
		mes = "00002</head>678";
		buffer.append(mes.getBytes());
		
		m = buffer.next();
		Assert.assertEquals("<head>00002</head>67", m);
	}
	
	@Test
	public void testInputOverFullAppend(){
		final ByteMessageBuffer buffer = new ByteMessageBuffer(22,charset);
		String mes = "<head>00005</head>12345";
		buffer.append(mes.getBytes());
		String m = buffer.next();
		Assert.assertNull(m);
	}
	
	@Test
	public void testBufferCompactFullAppend(){
		final ByteMessageBuffer buffer = new ByteMessageBuffer(40,charset);
		String mes = "<head>00005</head>12345";
		buffer.append(mes.getBytes());
		String m = buffer.next();
		Assert.assertEquals(mes, m);
		mes = "<head>00015</head>123456789abcdef";
		buffer.append(mes.getBytes());
		m = buffer.next();
		Assert.assertEquals(mes, m);
	}
	
	@Test
	public void testBufferOverFullAppend(){
		final ByteMessageBuffer buffer = new ByteMessageBuffer(40,charset);
		String mes = "<head>00005</head>12345";
		buffer.append(mes.getBytes());
		mes = "<head>00015</head>123456789abcdef";
		buffer.append(mes.getBytes());
		String m = buffer.next();
		Assert.assertEquals(mes, m);
	}
	
	@Test
	public void testBufferEmptyNext(){
		final ByteMessageBuffer buffer = new ByteMessageBuffer(40,charset);
		String m = buffer.next();
		Assert.assertNull(m);
	}
	
	@Test
	public void testBufferNotCompletionNext(){
		final ByteMessageBuffer buffer = new ByteMessageBuffer(40,charset);
		String mes = "<head>00005</head>12";
		buffer.append(mes.getBytes());
		String m = buffer.next();
		Assert.assertNull(m);
		mes = "345";
		buffer.append(mes.getBytes());
		m = buffer.next();
		Assert.assertEquals("<head>00005</head>12345", m);
	}
	
	@Test
	public void testMAXLengthNext(){
		final ByteMessageBuffer buffer = new ByteMessageBuffer(400,charset);
		String mes = "<head>32769</head>dddddd";
		buffer.append(mes.getBytes());
		String s = buffer.next();
		Assert.assertNull(s);
	}
	
	@Test
	public void testMissHead(){
		final ByteMessageBuffer buffer = new ByteMessageBuffer(400,charset);
		String mes = "12345678<head>00005</head>12345";
		buffer.append(mes.getBytes());
		String s = buffer.next();
		Assert.assertEquals("<head>00005</head>12345", s);
	}
}
