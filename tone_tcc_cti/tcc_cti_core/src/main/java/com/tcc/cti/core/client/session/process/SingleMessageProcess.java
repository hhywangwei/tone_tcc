package com.tcc.cti.core.client.session.process;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.receive.ParseMessageException;
import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.client.session.handler.ReceiveCollectionHandler;
import com.tcc.cti.core.client.session.handler.SendCollectionHandler;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

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
	private final Requests _requests = new Requests();
	

	private ReceiveHandler _receiveHandler = new ReceiveCollectionHandler();
	private SendHandler _sendHandler = new SendCollectionHandler();
	
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
	public void sendProcess(Sessionable session, Requestable<? extends Response> request,
			GeneratorSeq generator,	Charset charset)throws IOException {
		
		request.regsiterEvent(_requests);
		_sendHandler.send(session, request, generator, charset);
	}
	
	@Override
	public void receiveProcess(Sessionable session, String m) throws InterruptedException {
		if(StringUtils.isBlank(m)){
			return ;
		}
		MessageWapper w = new MessageWapper();
		w.session = session;
		w.message = m;
		_messageQueue.put(w);
	}
	
	@Override
	public void start() {
		if(_start){
			return ;
		}
		logger.debug("Message process start...");
		synchronized(_monitor){
			_processTask = new ProcessTask(
					_messageQueue, _receiveHandler,
					_requests, _warringLine);
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
	
	public void setReceiveHandler(ReceiveHandler handler){
		if(handler == null){
			throw new IllegalArgumentException("Receive handler not null");
		}
		_receiveHandler = handler;
	}
	
	public void setSendHandler(SendHandler handler){
		if(handler == null){
			throw new IllegalArgumentException("Send handler not null");
		}
		_sendHandler = handler;
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
		private final ReceiveHandler _handler;
		private final Requestsable _requests;
		private final int _warringLine;
		
		public ProcessTask(BlockingQueue<MessageWapper> queue,
				ReceiveHandler handler, Requestsable requests, int warringLine){
			
			_messageQueue = queue;
			_handler = handler;
			_requests = requests;
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
					receiveHandle(w.message, _requests, w.session, _handler);
				}catch(InterruptedException e){
					logger.error("Message process is interruped {}",e.getMessage());
					Thread.currentThread().interrupt();
				}
			}
		}
		
		private void receiveHandle(String m,Requestsable requests,
				Sessionable session,ReceiveHandler handler){
			
			if(StringUtils.isBlank(m)){
				return;
			}
			try{
				handler.receive(requests,session, m);	
			}catch(ParseMessageException e){
				logger.error("Receive {} handler exception {}", m, e.getMessage());
			}
		}
	}
}
