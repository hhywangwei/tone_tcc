package com.tcc.cti.core.client.session.handler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.tcc.cti.core.client.send.CallHelpSendHandler;
import com.tcc.cti.core.client.send.CallHoldSendHandler;
import com.tcc.cti.core.client.send.CommonSendHandler;
import com.tcc.cti.core.client.send.GroupSendHandler;
import com.tcc.cti.core.client.send.HeartbeatSendHandler;
import com.tcc.cti.core.client.send.LoginSendHandler;
import com.tcc.cti.core.client.send.MobileNumberSendHandler;
import com.tcc.cti.core.client.send.MonitorSendHandler;
import com.tcc.cti.core.client.send.OutCallCancelSendHandler;
import com.tcc.cti.core.client.send.OutCallSendHandler;
import com.tcc.cti.core.client.send.OwnSendHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.client.send.SilenceSendHandler;
import com.tcc.cti.core.client.send.StatusSendHandler;
import com.tcc.cti.core.client.send.TransferGroupSendHanlder;
import com.tcc.cti.core.client.send.TransferOneSendHandler;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

public class SendCollectionHandler implements SendHandler{
	private List<SendHandler> _handlers = new ArrayList<>();
	
	public SendCollectionHandler(){
		initHandlers(_handlers);
	}

    private void initHandlers(List<SendHandler> handlers){
    	
    	handlers.add(new CallHelpSendHandler());
    	handlers.add(new CallHoldSendHandler());
    	handlers.add(new CommonSendHandler());
		handlers.add(new GroupSendHandler());
		handlers.add(new HeartbeatSendHandler());
		handlers.add(new LoginSendHandler());
		handlers.add(new MobileNumberSendHandler());
		handlers.add(new MonitorSendHandler());
		handlers.add(new OutCallSendHandler());
		handlers.add(new OutCallCancelSendHandler());
		handlers.add(new OwnSendHandler());
		handlers.add(new SilenceSendHandler());
		handlers.add(new StatusSendHandler());
		handlers.add(new TransferGroupSendHanlder());
		handlers.add(new TransferOneSendHandler());
		
	}

    @Override
	public void send(Sessionable session,
			Requestable<? extends Response> request, GeneratorSeq generator,
			Charset charset) throws IOException {

		for(SendHandler handler : _handlers){
			handler.send(session, request, generator, charset);
		}	
	}
	
	public void addHandler(SendHandler handler){
		if(handler == null){
			throw new IllegalArgumentException("Send handler is not null");
		}
		_handlers.add(handler);
	}
	
	public void addHandlers(List<SendHandler> handlers){
		if(handlers == null){
			throw new IllegalArgumentException("Send handlers is not null");
		}
		_handlers.addAll(handlers);
	}
	
	public void setHandlers(List<SendHandler> handlers){
		if(handlers == null){
			throw new IllegalArgumentException("Send handlers is not null");
		}
		_handlers = handlers;
	}
}
