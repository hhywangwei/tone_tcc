package com.tcc.cti.core.client.buffer;

import org.junit.Assert;
import org.junit.Test;

public class ByteMessageBufferTest {
	
	@Test
	public void testAppend()throws Exception{
		ByteMessageBuffer buffer = new ByteMessageBuffer(100);
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
					Thread.sleep(1000);
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
					Thread.sleep(1000);
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
}
