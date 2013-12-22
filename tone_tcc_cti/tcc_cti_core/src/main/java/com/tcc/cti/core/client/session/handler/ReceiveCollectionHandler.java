package com.tcc.cti.core.client.session.handler;

import java.util.ArrayList;
import java.util.List;

import com.tcc.cti.core.client.receive.CallReceiveHandler;
import com.tcc.cti.core.client.receive.CloseCallReceiveHandler;
import com.tcc.cti.core.client.receive.CommonReceiveHandler;
import com.tcc.cti.core.client.receive.GroupMemberReceiveHandler;
import com.tcc.cti.core.client.receive.GroupReceiveHandler;
import com.tcc.cti.core.client.receive.HeartbeatReceiveHandler;
import com.tcc.cti.core.client.receive.LoginReceiveHandler;
import com.tcc.cti.core.client.receive.MonitorReceiveHandler;
import com.tcc.cti.core.client.receive.OutCallStateReceiveHandler;
import com.tcc.cti.core.client.receive.OwnReceiveHandler;
import com.tcc.cti.core.client.receive.ParseMessageException;
import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.client.session.process.Requestsable;

public class ReceiveCollectionHandler implements ReceiveHandler{
	
	private List<ReceiveHandler> _handlers = new ArrayList<>();
	
	public ReceiveCollectionHandler(){
		initHandlers(_handlers);
	}

	private void initHandlers(List<ReceiveHandler> handlers){
		handlers.add(new CommonReceiveHandler());
		handlers.add(new CallReceiveHandler());
		handlers.add(new CloseCallReceiveHandler());
		handlers.add(new GroupMemberReceiveHandler());
		handlers.add(new GroupReceiveHandler());
		handlers.add(new HeartbeatReceiveHandler());
		handlers.add(new LoginReceiveHandler());
		handlers.add(new MonitorReceiveHandler());
		handlers.add(new OutCallStateReceiveHandler());
		handlers.add(new OwnReceiveHandler());
	}
	
	@Override
	public void receive(Requestsable requests, Sessionable session,
			String message) throws ParseMessageException {
		
		for(ReceiveHandler handler : _handlers){
			handler.receive(requests,session, message);	
		}
	}
	
	public void addHandler(ReceiveHandler handler){
		if(handler == null){
			throw new IllegalArgumentException("Receive handler is not null");
		}
		_handlers.add(handler);
	}
	
	public void addHandlers(List<ReceiveHandler> handlers){
		if(handlers == null){
			throw new IllegalArgumentException("Receive handlers is not null");
		}
		_handlers.addAll(handlers);
	}
	
	public void setHandlers(List<ReceiveHandler> handlers){
		if(handlers == null){
			throw new IllegalArgumentException("Receive handlers is not null");
		}
		_handlers =handlers;
	}
}
