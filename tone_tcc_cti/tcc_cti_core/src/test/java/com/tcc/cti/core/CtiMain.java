package com.tcc.cti.core;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.client.Configure;
import com.tcc.cti.core.client.TcpCtiClient;
import com.tcc.cti.core.message.pool.CtiMessagePool;
import com.tcc.cti.core.message.pool.OperatorCtiMessagePool;
import com.tcc.cti.core.message.request.GroupRequest;
import com.tcc.cti.core.message.request.LoginRequest;
import com.tcc.cti.core.message.response.ResponseMessage;

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
	private final Thread _receiveThread ;
	
	public CtiMain()throws IOException{
		_opId = "8002";
		_companyId = "1";
		_pool = new OperatorCtiMessagePool();
		_client = new TcpCtiClient(initConfigure(),_pool);
		_receiveThread = new Thread(new ReceiveRunner(_pool,_companyId,_opId,_client));
	}
	
	private Configure initConfigure(){
		Configure configure = new Configure.
				Builder("211.136.173.132",9999).
				setCharsetName("GBK").
				build();
		
		return configure;
	}
	
	public void start()throws Exception{	
		_client.start();
		logger.debug("Start tcp client ...");
		_client.register(_companyId, _opId);
		logger.debug("Register company's {} and operator's {}",
				_companyId,_opId);
		_receiveThread.start();
		logger.debug("Start receive message");
	}
	
	private void login() throws ClientException{

		LoginRequest login = new LoginRequest();
		login.setCompayId(_companyId);
		login.setOpId(_opId);
		login.setOpNumber("8002");
		login.setPassword("1");
		login.setType("1");

		_client.send(login);;
	}
	
	private void getGroups()throws ClientException{
		
		GroupRequest r = new GroupRequest();
		
		r.setCompayId(_companyId);
		r.setOpId(_opId);
		_client.send(r);
	}
	
	public void close()throws ClientException{
		_receiveThread.interrupt();
	}
	
	public static void main(String[] args){
		try{
			CtiMain main = new CtiMain();
			main.start();
			main.login();
			main.getGroups();
			
			Thread.sleep(2* 60 * 1000);
			main.close();
		}catch(Exception e){
			logger.error("Test Cti is {}",e.toString());
		}
	}
	
	private static class ReceiveRunner implements Runnable{
		private static final String DEFAULT_MESSAGE_FILE = "cti_message.txt";
		
		private final CtiMessagePool _pool;
		private final OutputStream _os;
		private final String _companyId;
		private final String _opId;
		private final TcpCtiClient _client;
		
		ReceiveRunner(CtiMessagePool pool,String companyId,
				String opId,TcpCtiClient client)throws IOException{
			_pool = pool;
			_companyId = companyId;
			_opId = opId;
			_client = client;
			_os = new FileOutputStream(DEFAULT_MESSAGE_FILE);
		}
		
		
		@Override
		public void run(){
			
			while(true){
				if(Thread.interrupted()){
					try{
						logger.debug("Start close ...");
						_os.close();	
						_client.close();
					}catch(Exception e){
						logger.error("Close output stream is fail {}",e.toString());
					}
					return;
				}
				try {
					ResponseMessage m = _pool.poll(_companyId, _opId);
					if(m == null){
						logger.debug("Message is null");
						continue;
					}
					_os.write(m.toString().getBytes());
					_os.write('\n');
					_os.flush();
					logger.debug("Message is \"{}\"",m.toString());
				}catch (InterruptedException e){
					logger.error("Writ message InterruptedException");
					Thread.currentThread().interrupt();
				} catch (Exception e) {
					logger.error("Writ message is error {}",e.toString());
				}
			}
		}
	}
}
