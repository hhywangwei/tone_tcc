package com.tcc.cti.driver;


import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.driver.message.RequestTimeoutException;
import com.tcc.cti.driver.message.request.GroupRequest;
import com.tcc.cti.driver.message.request.LoginRequest;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.SessionFactory;
import com.tcc.cti.driver.session.Sessionable;

/**
 * 整合测试CTI
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class CtiMain {
	private static final Logger logger= LoggerFactory.getLogger(CtiMain.class);
	
	private final String _opId ;
	private final String _companyId;
	private final SessionFactory _sessionFactory;
	
	public CtiMain(String opId,String companyId){
		_opId = opId;
		_companyId = companyId;
		_sessionFactory =new SessionFactory(initConfigure());
	}
	
	private Configure initConfigure(){
		Configure configure = new Configure.
				Builder("211.136.173.132",9999).
				setCharsetName("GBK").
				setHeartbeatTimeout(30).
				build();
		
		return configure;
	}
	
	public Sessionable register(String opId,String companyId)throws Exception{
		Operator key = new Operator(companyId,opId);
		Sessionable session = _sessionFactory.getSession(key);
		return session;
	}
	
	private void login(Sessionable session) throws InterruptedException, IOException, RequestTimeoutException{

		LoginRequest login = new LoginRequest();
		login.setOpNumber("8002");
		login.setPassword("1");
		login.setType("1");

		session.send(login);
		
		List<Response> r = login.response();
		logger.debug("Login response is {}",r.toString());
	}

	private void getGroups(Sessionable session)throws IOException{
		GroupRequest r = new GroupRequest();
		session.send(r);
	}
	
	public void close()throws IOException{
		_sessionFactory.close();
	}
	
	public static void main(String[] args)throws Exception{
		String opId = "8002";
		String companyId = "1";
		CtiMain main = new CtiMain(opId,companyId);
		try{
			Sessionable session = main.register(opId, companyId);
			main.login(session);
//			Thread.sleep(60 * 1000);
//			main.getGroups(session);
//			Thread.sleep(60 * 1000);
		}finally{
			main.close();
		}
	}
//	
//	private static class ReceiveRunner implements Runnable{
//		private static final String DEFAULT_MESSAGE_FILE = "cti_message.txt";
//		
//		private final CtiMessagePool _pool;
//		private final OutputStream _os;
//		private final String _companyId;
//		private final String _opId;
//		private final TcpCtiClient _client;
//		
//		ReceiveRunner(CtiMessagePool pool,String companyId,
//				String opId,TcpCtiClient client)throws IOException{
//			_pool = pool;
//			_companyId = companyId;
//			_opId = opId;
//			_client = client;
//			_os = new FileOutputStream(DEFAULT_MESSAGE_FILE);
//		}
//		
//		
//		@Override
//		public void run(){
//			
//			while(true){
//				if(Thread.interrupted()){
//					try{
//						logger.debug("Start close ...");
//						_os.close();	
//						_client.close();
//					}catch(Exception e){
//						logger.error("Close output stream is fail {}",e.toString());
//					}
//					return;
//				}
//				try {
//					ResponseMessage m = _pool.poll(_companyId, _opId);
//					if(m == null){
//						logger.debug("Message is null");
//						continue;
//					}
//					_os.write(m.toString().getBytes());
//					_os.write('\n');
//					_os.flush();
//					logger.debug("Message is \"{}\"",m.toString());
//				}catch (InterruptedException e){
//					logger.error("Writ message InterruptedException");
//					Thread.currentThread().interrupt();
//				} catch (Exception e) {
//					logger.error("Writ message is error {}",e.toString());
//				}
//			}
//		}
//	}
}
