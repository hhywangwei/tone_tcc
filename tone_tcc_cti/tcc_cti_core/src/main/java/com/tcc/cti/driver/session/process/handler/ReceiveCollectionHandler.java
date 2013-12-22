package com.tcc.cti.driver.session.process.handler;

import java.util.ArrayList;
import java.util.List;

import com.tcc.cti.driver.session.Sessionable;
import com.tcc.cti.driver.session.process.Requestsable;
import com.tcc.cti.driver.session.process.handler.receive.CallReceiveHandler;
import com.tcc.cti.driver.session.process.handler.receive.CloseCallReceiveHandler;
import com.tcc.cti.driver.session.process.handler.receive.CommonReceiveHandler;
import com.tcc.cti.driver.session.process.handler.receive.GroupMemberReceiveHandler;
import com.tcc.cti.driver.session.process.handler.receive.GroupReceiveHandler;
import com.tcc.cti.driver.session.process.handler.receive.HeartbeatReceiveHandler;
import com.tcc.cti.driver.session.process.handler.receive.LoginReceiveHandler;
import com.tcc.cti.driver.session.process.handler.receive.MonitorReceiveHandler;
import com.tcc.cti.driver.session.process.handler.receive.OutCallStateReceiveHandler;
import com.tcc.cti.driver.session.process.handler.receive.OwnReceiveHandler;
import com.tcc.cti.driver.session.process.handler.receive.ParseMessageException;

public class ReceiveCollectionHandler implements ReceiveHandlerable{
	
	private List<ReceiveHandlerable> _handlers = new ArrayList<>();
	
	public ReceiveCollectionHandler(){
		initHandlers(_handlers);
	}

	private void initHandlers(List<ReceiveHandlerable> handlers){
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
		
		for(ReceiveHandlerable handler : _handlers){
			handler.receive(requests,session, message);	
		}
	}
	
	public void addHandler(ReceiveHandlerable handler){
		if(handler == null){
			throw new IllegalArgumentException("Receive handler is not null");
		}
		_handlers.add(handler);
	}
	
	public void addHandlers(List<ReceiveHandlerable> handlers){
		if(handlers == null){
			throw new IllegalArgumentException("Receive handlers is not null");
		}
		_handlers.addAll(handlers);
	}
	
	public void setHandlers(List<ReceiveHandlerable> handlers){
		if(handlers == null){
			throw new IllegalArgumentException("Receive handlers is not null");
		}
		_handlers =handlers;
	}
}
