package com.tcc.cti.core.client.task;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.client.buffer.MessageBuffer;
import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.message.pool.CtiMessagePool;

/**
 * 接收消息处理
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class MessageProcessTask implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(MessageProcessTask.class);
	
	private final List<ReceiveHandler> _receiveHandlers;
	private final CtiMessagePool _pool;
	private final Sessionable _session;
	private final MessageBuffer _mBuffer;
	private final Object _monitor = new Object();

	public MessageProcessTask(CtiMessagePool pool,Sessionable session,
			MessageBuffer mBuffer,List<ReceiveHandler> receiveHandlers){
		
		_pool = pool;
		_session = session;
		_mBuffer = mBuffer;
		_receiveHandlers = receiveHandlers;
	}
	
	@Override
	public void run() {
		while(true){
			try{
				if(Thread.interrupted()){
					break;
				}
				String m = null;
				synchronized (_monitor) {
					m = _mBuffer.next();
					if(m == null){
						_monitor.wait();
						continue;
					}
				}
				receiveHandle(m,_pool,_session,_receiveHandlers);	
			}catch(InterruptedException e){
				logger.error("Receive message interruped {}",e.getMessage());
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
			}catch(ClientException e){
				logger.error("Receive client exception {}",e.getMessage());
			}
		}
	}
	
	public void notifyReceive(){
		_monitor.notifyAll();
	}
}