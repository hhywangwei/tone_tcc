package com.tcc.cti.core.client.session.handler;

import java.util.ArrayList;
import java.util.List;

import com.tcc.cti.core.client.receive.CallHelpReceiveHandler;
import com.tcc.cti.core.client.receive.CallHoldReceiveHandler;
import com.tcc.cti.core.client.receive.CallReceiveHandler;
import com.tcc.cti.core.client.receive.CloseCallReceiveHandler;
import com.tcc.cti.core.client.receive.GroupMemberReceiveHandler;
import com.tcc.cti.core.client.receive.GroupReceiveHandler;
import com.tcc.cti.core.client.receive.HeartbeatReceiveHandler;
import com.tcc.cti.core.client.receive.LoginReceiveHandler;
import com.tcc.cti.core.client.receive.LogoutReceiveHandler;
import com.tcc.cti.core.client.receive.MobileNumberCancelReceiveHandler;
import com.tcc.cti.core.client.receive.MobileNumberReceiveHandler;
import com.tcc.cti.core.client.receive.MonitorReceiveHandler;
import com.tcc.cti.core.client.receive.OutCallCancelReceiveHandler;
import com.tcc.cti.core.client.receive.OutCallReceiveHandler;
import com.tcc.cti.core.client.receive.OutCallStateReceiveHandler;
import com.tcc.cti.core.client.receive.OwnReceiveHandler;
import com.tcc.cti.core.client.receive.ParseMessageException;
import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.receive.RecordReceiveHandler;
import com.tcc.cti.core.client.receive.RestReceiveHandler;
import com.tcc.cti.core.client.receive.ResumeReceiveHandler;
import com.tcc.cti.core.client.receive.SilenceReceiveHandler;
import com.tcc.cti.core.client.receive.StatusReceiveHandler;
import com.tcc.cti.core.client.receive.TransferGroupReceiveHanlder;
import com.tcc.cti.core.client.receive.TransferOneReceiveHandler;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.message.pool.CtiMessagePool;

public class ReceiveCollectionHandler implements ReceiveHandler{
	
	private List<ReceiveHandler> _handlers = new ArrayList<>();
	
	public ReceiveCollectionHandler(){
		initHandlers(_handlers);
	}

	private void initHandlers(List<ReceiveHandler> handlers){
		handlers.add(new CallHelpReceiveHandler());
		handlers.add(new CallHoldReceiveHandler());
		handlers.add(new CallReceiveHandler());
		handlers.add(new CloseCallReceiveHandler());
		handlers.add(new GroupMemberReceiveHandler());
		handlers.add(new GroupReceiveHandler());
		handlers.add(new HeartbeatReceiveHandler());
		handlers.add(new LoginReceiveHandler());
		handlers.add(new LogoutReceiveHandler());
		handlers.add(new MobileNumberCancelReceiveHandler());
		handlers.add(new MobileNumberReceiveHandler());
		handlers.add(new MonitorReceiveHandler());
		handlers.add(new OutCallCancelReceiveHandler());
		handlers.add(new OutCallReceiveHandler());
		handlers.add(new OutCallStateReceiveHandler());
		handlers.add(new OwnReceiveHandler());
		handlers.add(new RecordReceiveHandler());
		handlers.add(new RestReceiveHandler());
		handlers.add(new ResumeReceiveHandler());
		handlers.add(new SilenceReceiveHandler());
		handlers.add(new StatusReceiveHandler());
		handlers.add(new TransferGroupReceiveHanlder());
		handlers.add(new TransferOneReceiveHandler());
	}
	
	@Override
	public void receive(CtiMessagePool pool, Sessionable session, String message)
			throws ParseMessageException {
		for(ReceiveHandler handler : _handlers){
			handler.receive(pool,session, message);	
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
