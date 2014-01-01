package com.tcc.cti.web.server;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.tcc.cti.driver.Configure;
import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.RequestTimeoutException;
import com.tcc.cti.driver.message.request.LoginRequest;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.SessionFactory;
import com.tcc.cti.driver.session.SessionRegisterException;
import com.tcc.cti.driver.session.Sessionable;

public class CtiServerFac implements CtiServerFacable,InitializingBean {
	private static final String DEFAULT_HOST = "";
	private static final int DEFAULT_PORT = 21;
	
	private String _host = DEFAULT_HOST;
	private int _port = DEFAULT_PORT;
	private SessionFactory _sessionFactory;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Configure configure = new Configure.Builder(_host, _port).build();
		_sessionFactory = new SessionFactory(configure);
	}

	@Override
	public Response login(Operator operator, String password, String opNumber,String type)  {
		try{
			Sessionable session = _sessionFactory.getSession(operator);
			
			LoginRequest r = new LoginRequest();
			r.setOpNumber(opNumber);
			r.setPassword(password);
			r.setType(type);
			session.send(r);
			List<? extends Response> responses = r.response();
			return responses.get(0);
		}catch(SessionRegisterException e){
			
		}catch(IOException e){
			
		}catch(RequestTimeoutException e){
			
		}catch(InterruptedException e){
			
		}
		
		return null;
	}

	@Override
	public Response logout(Operator operator) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setHost(String host){
		_host = host;
	}
	
	public void setPort(int port){
		_port = port;
	}
}
