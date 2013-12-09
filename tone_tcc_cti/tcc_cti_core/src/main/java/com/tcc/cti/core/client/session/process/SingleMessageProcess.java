package com.tcc.cti.core.client.session.process;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.receive.ParseMessageException;
import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.message.pool.CtiMessagePool;

/**
 * 单线程消息处理
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class SingleMessageProcess implements MessageProcessable {
	private static final Logger logger = LoggerFactory.getLogger(SingleMessageProcess.class);
	private static final int DEFAULT_POOL_SIZE = 1024;

	private final BlockingQueue<MessageWapper> _messageQueue;
	private final Object _monitor = new Object();
	private List<ReceiveHandler> _handlers;
	private CtiMessagePool _messagePool;
	
	private volatile boolean _start = false;
	private volatile ProcessTask _processTask;
	
	public SingleMessageProcess(){
		this(DEFAULT_POOL_SIZE);
	}
	
	public SingleMessageProcess(int poolSize){
		_messageQueue = new ArrayBlockingQueue<MessageWapper>(poolSize);
	}
	
	@Override
	public void put(Sessionable session, String m) throws InterruptedException {
		MessageWapper w = new MessageWapper();
		w.session = session;
		w.message = m;
		_messageQueue.put(w);
	}

	@Override
	public void setReceiveHandlers(List<ReceiveHandler> handlers) {
		_handlers = handlers;
	}
	
	@Override
	public void setMessagePool(CtiMessagePool pool) {
		_messagePool = pool;
	}

	@Override
	public void start() {
		logger.debug("Message process start...");
		if(_handlers == null){
			throw new IllegalArgumentException("Send handlers must setting...");
		}
		if(_messagePool == null){
			throw new IllegalArgumentException("Message pool must setting...");
		}
		
		synchronized(_monitor){
			_processTask = new ProcessTask(_messageQueue, _handlers, _messagePool);
			Thread t = new Thread(_processTask);
			t.start();
			_start = true;
		}
	}

	@Override
	public void close() {
		synchronized (_monitor) {
			if(_start){
				_processTask.close();
			}
		}
	}
	
	protected static class MessageWapper{
		Sessionable session;
		String message;
	}

	protected static class ProcessTask implements Runnable{
		
		private final BlockingQueue<MessageWapper> _messageQueue;
		private final List<ReceiveHandler> _handlers;
		private final CtiMessagePool _messagePool;
		
		private volatile boolean _closed = false;
		
		public ProcessTask(BlockingQueue<MessageWapper> queue,
				List<ReceiveHandler> handlers,
				 CtiMessagePool pool){
			
			_messageQueue = queue;
			_handlers = handlers;
			_messagePool = pool;
		}

		@Override
		public void run() {
			
			while(true){
				try{
					if(_closed){
						logger.debug("Message process is close");
						break;
					}
					MessageWapper w = _messageQueue.take();
					receiveHandle(w.message,_messagePool,w.session,_handlers);
				}catch(InterruptedException e){
					logger.error("Message process is interruped {}",e.getMessage());
					Thread.interrupted();
				}
			}
		}
		
		private void receiveHandle(String m,CtiMessagePool pool,
				Sessionable session,List<ReceiveHandler> receiveHandlers){
			
			if(StringUtils.isBlank(m)){
				return;
			}
			for(ReceiveHandler handler : receiveHandlers){
				try{
					handler.receive(pool,session, m);	
				}catch(ParseMessageException e){
					logger.error("Receive process client exception {}",e.getMessage());
				}
			}
		}
		
		public void close(){
			_closed = false;
			Thread.currentThread().interrupt();
		}
	}

}
