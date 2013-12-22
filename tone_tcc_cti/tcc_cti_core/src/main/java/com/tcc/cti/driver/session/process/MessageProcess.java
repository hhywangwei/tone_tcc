package com.tcc.cti.driver.session.process;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.sequence.GeneratorSeq;
import com.tcc.cti.driver.session.Sessionable;
import com.tcc.cti.driver.session.process.handler.ReceiveCollectionHandler;
import com.tcc.cti.driver.session.process.handler.ReceiveHandlerable;
import com.tcc.cti.driver.session.process.handler.SendCollectionHandler;
import com.tcc.cti.driver.session.process.handler.SendHandlerable;
import com.tcc.cti.driver.session.process.handler.receive.ParseMessageException;

/**
 * 实现CTI服务器消息处理
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class MessageProcess implements MessageProcessable {
	private static final Logger logger = LoggerFactory.getLogger(MessageProcess.class);
	private static final Charset DEFAULT_CHARSET = Charset.forName("GBK");

	private final Requests _requests;
	private final Charset _charset;
	
	private ReceiveHandlerable _receiveHandler = new ReceiveCollectionHandler();
	private SendHandlerable _sendHandler = new SendCollectionHandler();
	
	public MessageProcess(){
		this(DEFAULT_CHARSET);
	}
	
	public MessageProcess(Charset charset){
		_charset = charset;
		_requests = new Requests();
	}
	
	@Override
	public void sendProcess(Sessionable session, Requestable<? extends Response> request,
			GeneratorSeq generator)throws IOException {
		
		request.regsiterEvent(_requests);
		_sendHandler.send(session, request, generator, _charset);
	}
	
	@Override
	public void receiveProcess(Sessionable session, String m) throws ParseMessageException {
		if(StringUtils.isNotBlank(m)){
			long start = System.currentTimeMillis();
			logger.debug("{}. start message process,message is {}.", start , m);
			
			_receiveHandler.receive(_requests,session, m);
			
			long end = System.currentTimeMillis();
			logger.debug("{}. end message process.", start);
			logger.debug("Message process time {}.", (end - start));
		}
	}
	
	
	public void setReceiveHandler(ReceiveHandlerable handler){
		if(handler == null){
			throw new IllegalArgumentException("Receive handler not null");
		}
		_receiveHandler = handler;
	}
	
	public void setSendHandler(SendHandlerable handler){
		if(handler == null){
			throw new IllegalArgumentException("Send handler not null");
		}
		_sendHandler = handler;
	}
}
