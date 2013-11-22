package com.tcc.cti.core.client.task;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.session.Sessionable;

/**
 * 从Stocket栈接收服务服务端信息
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class StocketReceiveTask implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(StocketReceiveTask.class);
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 8;
	
	private final Selector _selector;
	private final ByteBuffer _buffer ;
	private final Object _monitor = new Object();
	
	private volatile boolean _suspend = false;
	
	public StocketReceiveTask(Selector selector){
		this(selector,DEFAULT_BUFFER_SIZE);
	}
	
	public StocketReceiveTask(Selector selector,int bufferSize){
		_selector = selector;
		_buffer =  ByteBuffer.allocateDirect(bufferSize);
	}

	@Override
	public void run() {
		while(true){
			try{
				if(_suspend){
					logger.debug("StocketReaderTask is suspend...");
					synchronized (_monitor) {
						_monitor.wait();
						continue;
					}
				}

				if(Thread.interrupted()){
					logger.debug("Start close stocketReaderTask...");
					_selector.close();	
					break;
				}

				recevice(_selector,_buffer);

			}catch(InterruptedException e){
				logger.error("StocketReaderTask spspend is error:{}",e.toString());
				Thread.currentThread().interrupt();
			}catch(IOException e){
				//TODO 需要处理阻塞异常
				//TODO 可能需要更多异常处理
				logger.error("StocketReaderTask message is error {}",e);
			}
		}
	}
	
	private void recevice(Selector selector,ByteBuffer buffer)throws IOException{
		
		if(_selector.select() == 0){
			return ;
		}
		
		try{
			Set<SelectionKey> selectedKeys = _selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectedKeys.iterator();					
			while(iterator.hasNext()){
				SelectionKey sk = iterator.next();
				appendBuffer(buffer,sk);
				iterator.remove();
			}	
		}catch(Exception e){
			logger.error("receive start...");
		}
		
	}
	
	/**
	 * 接收服务端数据保存到缓存区
	 * 
	 * @param buffer {@link ByteBuffer}
	 * @param sk {@link SelectionKey}
	 * @throws IOException
	 */
	private void appendBuffer(ByteBuffer buffer,SelectionKey sk)throws IOException{
		if(!sk.isReadable()) {
			return ;
		}
		SocketChannel sc =(SocketChannel) sk.channel();
		int len = sc.read(buffer);
		Sessionable oc = (Sessionable)sk.attachment();
		if(len == 0 || len == -1){
			logger.debug("{} server close...",oc.getOperatorKey().toString());
			oc.close();
		}else{
			buffer.flip();
			byte[] bytes = new byte[buffer.remaining()];
			buffer.get(bytes);
			oc.append(bytes);
			buffer.clear();	
		}
	}
	
	/**
	 * 从新开始{@link Selector.select()},接收注册服务端数据
	 */
	public void restart(){
		_suspend = false;
		synchronized (_monitor) {
			logger.debug("ReaderRunner is restart");
			_monitor.notifyAll();
		}
	}
	
	/**
	 * 暂停{@link Selector.select()}并从中断恢复，使{@link Selector}可以接收其他操作
	 */
	public void suspend() {
		synchronized (_monitor) {
			_suspend = true;
			_selector.wakeup();	
		}
	}
}