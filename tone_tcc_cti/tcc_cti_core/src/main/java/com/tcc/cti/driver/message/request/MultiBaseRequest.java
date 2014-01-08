package com.tcc.cti.driver.message.request;

import java.util.ArrayList;

import com.tcc.cti.driver.message.response.Response;

/**
 * 请求返回多消息类型
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 * @param <T> 返回消息类型
 */
public class MultiBaseRequest<T extends Response> extends BaseRequest<T> {
	private static final String COMPLETE_PREFIX = "-";
	public MultiBaseRequest(String messageType) {
		super(messageType,new ArrayList<T>());
	}
	
	@Override
	public void receive(T response) {
		synchronized (_monitor) {
			_complete = isComplete(response);
			if(_complete){
				_monitor.notifyAll();
			}else{
				_responses.add(response);
			}
		}
	}
	
	private boolean isComplete(T response){
		String seq = response.getSeq();
		if(seq == null){
			return false;
		}
		return seq.startsWith(COMPLETE_PREFIX);
	}
}
