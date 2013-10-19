package com.tcc.cti.core;


import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.client.TcpCtiClient;
import com.tcc.cti.core.client.receive.CallReceiveHandler;
import com.tcc.cti.core.client.receive.CloseCallReceiveHandler;
import com.tcc.cti.core.client.receive.GroupMemberReceiveHandler;
import com.tcc.cti.core.client.receive.GroupReceiveHandler;
import com.tcc.cti.core.client.receive.LoginReceiveHandler;
import com.tcc.cti.core.client.receive.MonitorReceiveHandler;
import com.tcc.cti.core.client.receive.OutCallReceiveHandler;
import com.tcc.cti.core.client.receive.OutCallStateReceiveHandler;
import com.tcc.cti.core.client.receive.OwnReceiveHandler;
import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.receive.RecordReceiveHandler;
import com.tcc.cti.core.client.send.CallSendHandler;
import com.tcc.cti.core.client.send.GroupMemberSendHandler;
import com.tcc.cti.core.client.send.GroupSendHandler;
import com.tcc.cti.core.client.send.LoginSendHandler;
import com.tcc.cti.core.client.send.MonitorSendHandler;
import com.tcc.cti.core.client.send.OutCallCancelSendHandler;
import com.tcc.cti.core.client.send.OutCallSendHandler;
import com.tcc.cti.core.client.send.OwnSendHandler;
import com.tcc.cti.core.client.send.RecordSendHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.message.pool.CtiMessagePool;
import com.tcc.cti.core.message.pool.OperatorCtiMessagePool;
import com.tcc.cti.core.message.request.LoginRequest;
import com.tcc.cti.core.message.response.ResponseMessage;
import com.tcc.cti.core.model.ServerConfigure;

/**
 * 整合测试CTI
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class CtiMain {
	private static final Logger logger= LoggerFactory.getLogger(CtiMain.class);
	
	private final String _opId ;
	private final String _companyId;
	private final TcpCtiClient _client;
	private final CtiMessagePool _pool;
	
	public CtiMain(){
		_opId = "8002";
		_companyId = "1";
		_pool = new OperatorCtiMessagePool();
		_client = new TcpCtiClient(initConfigure(),_pool);
		_client.setReceiveHandlers(initReceiveHandlers());
		_client.setSendHandlers(initSendHandlers());
	}
	
	public void start()throws Exception{	
		_client.start();
		logger.debug("Start tcp client ...");
		_client.register(_companyId, _opId);
		logger.debug("Register company's {} and operator's {}",
				_companyId,_opId);
		
		loginCtiServer();
	}
	
	private void loginCtiServer() throws ClientException{

		LoginRequest login = new LoginRequest();
		login.setCompayId(_companyId);
		login.setOpId(_opId);
		login.setOpNumber("8002");
		login.setPassword("1");
		login.setType("1");

		_client.send(login);;
	}
	
	private ServerConfigure initConfigure(){
		ServerConfigure configure = new ServerConfigure();
		configure.setHost("211.136.173.132");
		configure.setPort(9999);
		
		return configure;
	}
	
	private List<ReceiveHandler> initReceiveHandlers(){
		List<ReceiveHandler> handlers = new ArrayList<>();
		
		handlers.add(new LoginReceiveHandler());
		handlers.add(new OwnReceiveHandler());
		handlers.add(new GroupMemberReceiveHandler());
		handlers.add(new GroupReceiveHandler());
		handlers.add(new MonitorReceiveHandler());
		handlers.add(new OutCallReceiveHandler());
		handlers.add(new OutCallStateReceiveHandler());
		handlers.add(new CallReceiveHandler());
		handlers.add(new CloseCallReceiveHandler());
		handlers.add(new RecordReceiveHandler());
		
		return handlers;
	}
	
	private List<SendHandler> initSendHandlers(){
		List<SendHandler> handlers = new ArrayList<>();
		
		handlers.add(new LoginSendHandler());
		handlers.add(new OwnSendHandler());
		handlers.add(new GroupMemberSendHandler());
		handlers.add(new GroupSendHandler());
		handlers.add(new MonitorSendHandler());
		handlers.add(new OutCallSendHandler());
		handlers.add(new OutCallCancelSendHandler());
		handlers.add(new CallSendHandler());
		handlers.add(new RecordSendHandler());
		
		return handlers;
	}
	
	public static void main(String[] args){
		CtiMain main = new CtiMain();
		try{
			main.start();
		}catch(Exception e){
			logger.error("Test Cti is {}",e.toString());
		}
	}
	
	private static class ReceiveRunner implements Runnable{
		
		private final CtiMessagePool _pool;
		private final OutputStream _os;
		private final String _companyId;
		private final String _opId;
		
		ReceiveRunner(CtiMessagePool pool,OutputStream os,
				String companyId,String opId){
			_pool = pool;
			_os = os;
			_companyId = companyId;
			_opId = opId;
		}
		
		
		@Override
		public void run(){
			
			while(true){
//				ResponseMessage m = _pool.poll(_companyId, _opId);
			}
		}
	}
}
