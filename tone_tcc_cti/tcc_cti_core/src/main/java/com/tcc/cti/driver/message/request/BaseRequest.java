package com.tcc.cti.driver.message.request;

import java.util.ArrayList;
import java.util.List;

import com.tcc.cti.driver.message.RequestTimeoutException;
import com.tcc.cti.driver.message.event.NoneRequestEvent;
import com.tcc.cti.driver.message.event.RequestEvent;
import com.tcc.cti.driver.message.response.Response;

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
	private static final int DEFAULT_TIMEOUT = 10 * 1000;
	private static final int RESPONSES_SIZE = 1;

	protected final String _messageType ;
	protected final List<T> _responses ;
	protected final Object _monitor ;
	protected volatile boolean _complete = false;	
	private volatile RequestEvent _event = new NoneRequestEvent();
	private volatile String _seq = "";
	
	public BaseRequest(String messageType){
		this(messageType, new ArrayList<T>(RESPONSES_SIZE));
	}
	
	protected BaseRequest(String messageType,List<T> responses){
		_responses = responses;
		_messageType = messageType;
		_monitor = new Object();
	}

	@Override
	public void regsiterEvent(RequestEvent event){
		_event = event;
	}
	
	public String getMessageType(){
		return _messageType;
	}
	
	@Override
	public void notifySend(String seq){
		synchronized (_monitor) {
			_event.startRequest(_messageType, seq, this);
			_seq = seq;
		}
	}
	
	@Override
	public void notifySendError(Throwable e){
		synchronized (_monitor) {
			_event.finishRequest(_messageType, _seq);	
		}
	}
	
	@Override
	public void receive(T response) {
		synchronized (_monitor) {
			_responses.add(response);	
			_complete = true;
			_monitor.notifyAll();
		}
	}
	

	@Override
	public List<T> response() throws InterruptedException, RequestTimeoutException {
		return response(DEFAULT_TIMEOUT);
	}
	
	@Override
	public List<T> response(int timeout) throws InterruptedException, RequestTimeoutException {
		synchronized (_monitor) {
			_monitor.wait(timeout);
			_event.finishRequest(_messageType, _seq);
			if(_complete){
				return _responses;	
			}else{
				throw new RequestTimeoutException(_seq);
			}
		}
	}
	
	protected List<T> responseNow(){
		synchronized (_monitor) {
			return _responses;
		}
	}
}
