package com.tcc.cti.core.client.buffer;

import org.junit.Assert;
import org.junit.Test;

/**
 * 单元测试{@link ByteMessageBuffer}
 * 
 * @author <a href="hhywangwei@gmail.com">wangewei</a>
 */
public class ByteMessageBufferTest {
	
	@Test
	public void testAppend()throws Exception{
		ByteMessageBuffer buffer = new ByteMessageBuffer(100);
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
	public void testNext()throws Exception{
		String mes = "<head>00005</head>12345<head>";
		ByteMessageBuffer buffer = new ByteMessageBuffer(100);
		buffer.append(mes.getBytes());
		
		String m = buffer.next();
		Assert.assertEquals("<head>00005</head>12345", m);
		
		mes = "00002</head>678";
		buffer.append(mes.getBytes());
		
		m = buffer.next();
		Assert.assertEquals("<head>00002</head>67", m);
	}
	
	@Test
	public void testBufferFullAppend()throws Exception{
		final ByteMessageBuffer buffer = new ByteMessageBuffer(40);
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				try{
					Thread.sleep(500);
					buffer.next();
				}catch(Exception e){
					Assert.fail(e.getMessage());
				}
			}
		});
		t.start();
		String mes = "<head>00005</head>12345";
		buffer.append(mes.getBytes());
		mes = "<head>00015</head>123456789abcdef";
		buffer.append(mes.getBytes());
		String m = buffer.next();
		Assert.assertEquals(mes, m);
	}
	
	@Test
	public void testBufferEmptyNext()throws Exception{
		final ByteMessageBuffer buffer = new ByteMessageBuffer(40);
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				try{
					Thread.sleep(500);
					String mes = "<head>00005</head>12345";
					buffer.append(mes.getBytes());
				}catch(Exception e){
					Assert.fail(e.getMessage());
				}
			}
		});
		t.start();
		String m = buffer.next();
		Assert.assertEquals("<head>00005</head>12345", m);
	}
	
	@Test
	public void testBufferNotCompletionNext()throws Exception{
		final ByteMessageBuffer buffer = new ByteMessageBuffer(40);
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				try{
					Thread.sleep(500);
					String mes = "345";
					buffer.append(mes.getBytes());
				}catch(Exception e){
					Assert.fail(e.getMessage());
				}
			}
		});
		t.start();
		String mes = "<head>00005</head>12";
		buffer.append(mes.getBytes());
		String m = buffer.next();
		Assert.assertEquals("<head>00005</head>12345", m);
	}
	
	@Test
	public void testMAXLengthNext()throws Exception{
		final ByteMessageBuffer buffer = new ByteMessageBuffer(400);
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				try{
					Thread.sleep(500);
					String mes = "<head>00005</head>12345";
					buffer.append(mes.getBytes());
				}catch(Exception e){
					Assert.fail(e.getMessage());
				}
			}
		});
		t.start();
		String mes = "<head>32769</head>dddddd";
		buffer.append(mes.getBytes());
		String s = buffer.next();
		Assert.assertEquals("<head>00005</head>12345", s);
	}
	
	@Test
	public void testMissHead()throws Exception{
		final ByteMessageBuffer buffer = new ByteMessageBuffer(400);
		String mes = "12345678<head>00005</head>12345";
		buffer.append(mes.getBytes());
		String s = buffer.next();
		Assert.assertEquals("<head>00005</head>12345", s);
	}
}
