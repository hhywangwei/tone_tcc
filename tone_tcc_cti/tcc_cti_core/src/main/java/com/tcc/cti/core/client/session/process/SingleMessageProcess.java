package com.tcc.cti.core.client.session.process;

import java.util.Date;
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
	private static final int DEFAULT_POOL_SIZE = 5 * 1024;
	private static final int WARRING_LINE_PERCENT = 70 ;

	private final BlockingQueue<MessageWapper> _messageQueue;
	private final int _warringLine ;
	private final Object _monitor = new Object();
	private List<ReceiveHandler> _handlers;
	private CtiMessagePool _messagePool;
	
	
	private volatile boolean _start = false;
	private volatile ProcessTask _processTask;
	private volatile Thread _processTaskThread;
	
	public SingleMessageProcess(){
		this(DEFAULT_POOL_SIZE);
	}
	
	public SingleMessageProcess(int poolSize){
		_messageQueue = new ArrayBlockingQueue<MessageWapper>(poolSize);
		_warringLine = (poolSize * WARRING_LINE_PERCENT) / 100;
	}
	
	@Override
	public void put(Sessionable session, String m) throws InterruptedException {
		if(StringUtils.isBlank(m)){
			return ;
		}
		MessageWapper w = new MessageWapper();
		w.session = session;
		w.message = m;
		_messageQueue.put(w);
	}

	@Override
	public void setReceiveHandlers(List<ReceiveHandler> handlers) {
		if(handlers == null){
			throw new IllegalArgumentException("Receive handlers not null");
		}
		_handlers = handlers;
	}
	
	@Override
	public void setMessagePool(CtiMessagePool pool) {
		if(pool == null){
			throw new IllegalArgumentException("Message poll not null");
		}
		_messagePool = pool;
	}

	@Override
	public void start() {
		if(_start){
			return ;
		}
		if(_handlers == null){
			throw new IllegalArgumentException("Send handlers not null");
		}
		if(_messagePool == null){
			throw new IllegalArgumentException("Message pool not null");
		}
		
		logger.debug("Message process start...");
		synchronized(_monitor){
			_processTask = new ProcessTask(
					_messageQueue, _handlers,
					_messagePool,_warringLine);
			_processTaskThread = new Thread(_processTask);
			_processTaskThread.start();
			_start = true;
		}
	}

	@Override
	public void close() {
		synchronized (_monitor) {
			if(_start){
				_processTaskThread.interrupt();
			}
		}
	}
	
	protected int getQueueSize(){
		return _messageQueue.size();			
	}
	
	@Override
	public boolean isStart(){
		return _start;
	}
	
	protected MessageWapper task()throws InterruptedException{
		return _messageQueue.take();		
	}
	
	protected static class MessageWapper{
		Sessionable session;
		String message;
	}

	protected static class ProcessTask implements Runnable{
		
		private final BlockingQueue<MessageWapper> _messageQueue;
		private final List<ReceiveHandler> _handlers;
		private final CtiMessagePool _messagePool;
		private final int _warringLine;
		
		public ProcessTask(BlockingQueue<MessageWapper> queue,
				List<ReceiveHandler> handlers,
				 CtiMessagePool pool,int warringLine){
			
			_messageQueue = queue;
			_handlers = handlers;
			_messagePool = pool;
			_warringLine = warringLine;
		}

		@Override
		public void run() {
			
			while(true){
				try{
					if(Thread.interrupted()){
						logger.debug("Message process is close");
						break;
					}
					int len = _messageQueue.size();
					if(len > _warringLine ){
						Date now = new Date();
						logger.error("{}. have message {} process",now,len);
					}
					MessageWapper w = _messageQueue.take();
					receiveHandle(w.message,_messagePool,w.session,_handlers);
				}catch(InterruptedException e){
					logger.error("Message process is interruped {}",e.getMessage());
					Thread.currentThread().interrupt();
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
	}
}
