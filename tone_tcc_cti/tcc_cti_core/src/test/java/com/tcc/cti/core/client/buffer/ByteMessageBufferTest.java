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
		Assert.assertEquals(0, buffer.getPosition());
		Assert.assertEquals(10, buffer.getLimit());
		
		bytes = "efg".getBytes();
		buffer.append(bytes);
		Assert.assertEquals(0, buffer.getPosition());
		Assert.assertEquals(13, buffer.getLimit());
	}
	
	@Test
	public void testNext()throws Exception{
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
	public void testNextNotHead()throws Exception{
		String mes = "dddddddd<head>00005</head>12345<head>";
		ByteMessageBuffer buffer = new ByteMessageBuffer(100,charset);
		buffer.append(mes.getBytes());
		
		String m = buffer.next();
		Assert.assertEquals("<head>00005</head>12345", m);
	}
	
	@Test
	public void testInputOverFullAppend(){
		final ByteMessageBuffer buffer = new ByteMessageBuffer(22,charset);
		String mes = "<head>00005</head>12345";
		buffer.append(mes.getBytes());
		Assert.assertEquals(0, buffer.getLimit());
	}
	
	@Test
	public void testBufferOverFullAppend()throws InterruptedException{
		final ByteMessageBuffer buffer = new ByteMessageBuffer(40,charset);
		String mes = "<head>00005</head>12345";
		buffer.append(mes.getBytes());
		mes = "<head>00015</head>123456789abcdef";
		buffer.append(mes.getBytes());
		String m = buffer.next();
		Assert.assertEquals(mes, m);
	}
	
	@Test
	public void testBufferEmptyNextWait(){
		final ByteMessageBuffer buffer = new ByteMessageBuffer(40,charset);
		try{
			interruptWait();
			buffer.next();	
			Assert.fail("Next wait is error");
		}catch(InterruptedException e){
			//none instance
		}
	}
	
	private void interruptWait(){
		final Thread current = Thread.currentThread();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					Thread.sleep(1000);	
					current.interrupt();
				}catch(InterruptedException e){
					Assert.fail("sleep is error");
				}
			}
		});
		t.start();
	}
	
	@Test
	public void testBufferNotCompletionNext()throws InterruptedException{
		final ByteMessageBuffer buffer = new ByteMessageBuffer(40,charset);
		String mes = "<head>00005</head>12";
		buffer.append(mes.getBytes());
		waitWrite(buffer,"345");
		String m = buffer.next();
		Assert.assertEquals("<head>00005</head>12345", m);
	}
	
	private void waitWrite(final ByteMessageBuffer buffer,final String m){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					Thread.sleep(1000);	
					buffer.append(m.getBytes());
				}catch(InterruptedException e){
					Assert.fail("sleep is error");
				}
			}
		});
		t.start();
	}
	
	@Test
	public void testMAXLengthNoneNext(){
		final ByteMessageBuffer buffer = new ByteMessageBuffer(400,charset);
		String mes = "<head>32769</head>dddddd";
		buffer.append(mes.getBytes());
		try{
			interruptWait();
			buffer.next();	
			Assert.fail("Next wait is error");
		}catch(InterruptedException e){
			Assert.assertTrue(buffer.getPosition()==0);
			Assert.assertTrue(buffer.getLimit()==0);
		}
	}
	
	@Test
	public void testMissMessage()throws InterruptedException{
		final ByteMessageBuffer buffer = new ByteMessageBuffer(400,charset);
		String mes = "12345678<head>";
		buffer.append(mes.getBytes());
		mes ="<head>00005</head>12345"; 
		waitWrite(buffer,mes);
		String m = buffer.next();	
		Assert.assertEquals(mes, m);
		Assert.assertTrue(buffer.getPosition()== buffer.getLimit());
	}
}
