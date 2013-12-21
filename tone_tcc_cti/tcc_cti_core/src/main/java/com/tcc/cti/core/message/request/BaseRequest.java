package com.tcc.cti.core.message.request;

import java.util.ArrayList;
import java.util.List;

import com.tcc.cti.core.message.event.NoneRequestEvent;
import com.tcc.cti.core.message.event.RequestEvent;
import com.tcc.cti.core.message.response.Response;

/**
 * 发送消息
 * 
 * <pre>
 * _messageType:消息类型
 * </pre>
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 *
 */
public class BaseRequest<T extends Response> implements Requestable<T>{
	private static final int DEFAULT_TIMEOUT = 30 * 1000;

	protected final String _messageType ;
	protected final List<T> _responses ;
	protected final Object _monitor ;
	private final int _timeout ;
	
	private volatile RequestEvent _event = new NoneRequestEvent();
	private volatile String _seq = "";
	
	public BaseRequest(String messageType){
		this(messageType, -1, DEFAULT_TIMEOUT);
	}
	
	public BaseRequest(String messageType,int size){
		this(messageType, size, DEFAULT_TIMEOUT);
	}
	
	public BaseRequest(String messageType,int size,int timeout){
		_responses = (size <= 0) ?  new ArrayList<T>() : new ArrayList<T>(size);
		_messageType = messageType;
		_monitor = new Object();
		_timeout = timeout;
	}

	@Override
	public void regsiterEvent(RequestEvent event){
		_event = event;
	}
	
	public String getMessageType(){
		return _messageType;
	}
	
	@Override
	public void send(String seq){
		synchronized (_monitor) {
			_event.startRequest(_messageType, seq, this);
			_seq = seq;
		}
	}
	
	@Override
	public void sendError(Throwable e){
		synchronized (_monitor) {
			_event.finishRequest(_messageType, _seq);	
		}
	}
	
	@Override
	public void receive(T response) {
		synchronized (_monitor) {
			boolean complete = isComplete(response);
			if(!complete){
				_responses.add(response);	
				_monitor.notifyAll();
			}
		}
	}
	
	protected boolean isComplete(T response){
		return true;
	}

	@Override
	public List<T> respone() throws InterruptedException {
		synchronized (_monitor) {
			_monitor.wait(_timeout);
			_event.finishRequest(_messageType, _seq);
			return _responses; 
		}
	}
}
