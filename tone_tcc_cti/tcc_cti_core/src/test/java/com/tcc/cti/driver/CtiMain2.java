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
public class CtiMain2 {
	private static final Logger logger= LoggerFactory.getLogger(CtiMain2.class);
	
	private final String _opId ;
	private final String _companyId;
	private final SessionFactory _sessionFactory;
	
	public CtiMain2(String opId,String companyId){
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
		CtiMain2 main = new CtiMain2(opId,companyId);
		try{
			Sessionable session = main.register(opId, companyId);
			main.login(session);
			main.getGroups(session);
		}finally{
			main.close();
		}
	}
	
	 
}
