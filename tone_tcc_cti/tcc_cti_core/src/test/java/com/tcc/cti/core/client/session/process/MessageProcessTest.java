package com.tcc.cti.core.client.session.process;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.client.session.Sessionable;
import com.tcc.cti.core.client.session.process.handler.ReceiveHandlerable;
import com.tcc.cti.core.client.session.process.handler.receive.ParseMessageException;
import com.tcc.cti.core.message.event.RequestEvent;
import com.tcc.cti.core.message.request.Requestable;


/**
 * {@link MessageProcess}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class MessageProcessTest {
	
	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void testSendProcess()throws IOException{
		MessageProcess process = new MessageProcess();
		Sessionable session = Mockito.mock(Sessionable.class);
		Requestable request = Mockito.mock(Requestable.class);
		GeneratorSeq generator = Mockito.mock(GeneratorSeq.class);
		
		process.sendProcess(session, request, generator);
		
		Mockito.verify(request,Mockito.atLeastOnce()).regsiterEvent(Mockito.any(RequestEvent.class));
	}
	
	@Test
	public void testReceiveProcessIsBlank()throws ParseMessageException{
		MessageProcess process = new MessageProcess();
		Sessionable session = Mockito.mock(Sessionable.class);
		ReceiveHandlerable handler = Mockito.mock(ReceiveHandlerable.class);
		process.setReceiveHandler(handler);
		
		process.receiveProcess(session, "");
		Mockito.verify(handler,Mockito.never()).receive(
				Mockito.any(Requestsable.class), Mockito.any(Sessionable.class), Mockito.anyString());;
	}
	
	@Test
	public void testReceiveProcess()throws ParseMessageException{
		MessageProcess process = new MessageProcess();
		Sessionable session = Mockito.mock(Sessionable.class);
		ReceiveHandlerable handler = Mockito.mock(ReceiveHandlerable.class);
		process.setReceiveHandler(handler);
		
		process.receiveProcess(session, "dddd");
		Mockito.verify(handler,Mockito.atLeastOnce()).receive(
				Mockito.any(Requestsable.class), Mockito.any(Sessionable.class), Mockito.anyString());;
	}
}
