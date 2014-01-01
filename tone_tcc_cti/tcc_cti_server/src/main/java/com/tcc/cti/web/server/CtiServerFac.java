package com.tcc.cti.web.server;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.tcc.cti.driver.Configure;
import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.RequestTimeoutException;
import com.tcc.cti.driver.message.request.LoginRequest;
import com.tcc.cti.driver.message.request.LogoutRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.SessionFactory;
import com.tcc.cti.driver.session.SessionRegisterException;
import com.tcc.cti.driver.session.Sessionable;

@Service
public class CtiServerFac implements CtiServerFacable,InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(CtiServerFac.class);
	
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
	public Response login(Operator operator, String password, String opNumber,String type)throws CtiServerException{
			LoginRequest r = new LoginRequest();
			r.setOpNumber(opNumber);
			r.setPassword(password);
			r.setType(type);
			Sessionable session = getSession(operator);			
			send(session,r);
			return responseSingle(r);
	}
	
	private Sessionable getSession(Operator operator)throws CtiServerException{
		try{
			return _sessionFactory.getSession(operator);
		}catch(SessionRegisterException e){
			logger.debug("{} session register is fail,Error is {}",
					operator.toString(),e.toString());
			throw new CtiServerException(e);
		}
	}
	
	private void send(Sessionable session,Requestable<? extends Response> request)throws CtiServerException{
		try{
			session.send(request);
		}catch(IOException e){
			logger.debug("Send {} request is fail,Error is {}",
					request.toString(),e.toString());
			throw new CtiServerException(e);
		}
	}
	
	private Response responseSingle(Requestable<? extends Response> request)throws CtiServerException{
		try{
			List<? extends Response> responses = request.response();
			return responses.isEmpty() ? null : responses.get(0);
		}catch(RequestTimeoutException e){
			logger.debug("{} request timeout,Error is {}",
					request.toString(),e.toString());
			throw new CtiServerException(e);
		}catch(InterruptedException e){
			logger.debug("{} interrupted exception,Error is {}",
					request.toString(),e.toString());
			throw new CtiServerException(e);
		}
	}

	@Override
	public Response logout(Operator operator)throws CtiServerException{
		Sessionable session = getSession(operator);
		LogoutRequest r = new LogoutRequest();
		send(session,r);
		return responseSingle(r);
	}
	
	public void setHost(String host){
		_host = host;
	}
	
	public void setPort(int port){
		_port = port;
	}
}
