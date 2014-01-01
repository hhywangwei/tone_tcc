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
import com.tcc.cti.driver.message.request.CallHelpRequest;
import com.tcc.cti.driver.message.request.CallHoldRequest;
import com.tcc.cti.driver.message.request.CallRequest;
import com.tcc.cti.driver.message.request.GroupMemberRequest;
import com.tcc.cti.driver.message.request.GroupRequest;
import com.tcc.cti.driver.message.request.LoginRequest;
import com.tcc.cti.driver.message.request.LogoutRequest;
import com.tcc.cti.driver.message.request.MobileNumberCancelRequest;
import com.tcc.cti.driver.message.request.MobileNumberRequest;
import com.tcc.cti.driver.message.request.MonitorRequest;
import com.tcc.cti.driver.message.request.OutCallCancelRequest;
import com.tcc.cti.driver.message.request.OutCallRequest;
import com.tcc.cti.driver.message.request.OwnRequest;
import com.tcc.cti.driver.message.request.PasswordRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.request.RestRequest;
import com.tcc.cti.driver.message.request.ResumeRequest;
import com.tcc.cti.driver.message.request.SilenceRequest;
import com.tcc.cti.driver.message.request.TransferGroupRequest;
import com.tcc.cti.driver.message.request.TransferOneRequest;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.SessionFactory;
import com.tcc.cti.driver.session.SessionRegisterException;
import com.tcc.cti.driver.session.Sessionable;

/**
 * 实现{@link CtiServerFacable}接口,该实现依赖spring框架。
 * 
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class CtiServerFac implements CtiServerFacable,InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(CtiServerFac.class);
	
	private static final String DEFAULT_HOST = "211.136.173.132";
	private static final int DEFAULT_PORT = 9999;
	
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

	@Override
	public Response logout(Operator operator)throws CtiServerException{
		Sessionable session = getSession(operator);
		LogoutRequest r = new LogoutRequest();
		send(session,r);
		return responseSingle(r);
	}
	
	@Override
	public List<Response> getGroups(Operator operator, String groupId)
			throws CtiServerException {
		Sessionable session = getSession(operator);
		GroupRequest r = new GroupRequest();
		r.setGroupId(groupId);
		send(session,r);
		return response(r);
	}
	
	@Override
	public List<Response> getGroupMembers(Operator operator, String groupId)
			throws CtiServerException {
		Sessionable session = getSession(operator);
		GroupMemberRequest r = new GroupMemberRequest();
		r.setGroupId(groupId);
		send(session,r);
		return response(r);
	}

	@Override
	public Response getOwnInfo(Operator operator) throws CtiServerException {
		Sessionable session = getSession(operator);
		OwnRequest r = new OwnRequest();
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public Response getWorkInfo(Operator operator, String workId)
			throws CtiServerException {
		Sessionable session = getSession(operator);
		OwnRequest r = new OwnRequest();
		r.setWorkId(workId);
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public List<Response> getMonitors(Operator operator)
			throws CtiServerException {
		Sessionable session = getSession(operator);
		MonitorRequest r = new MonitorRequest();
		send(session,r);
		return response(r);
	}

	@Override
	public List<Response> getCall(Operator operator) throws CtiServerException {
		Sessionable session = getSession(operator);
		CallRequest r = new CallRequest();
		send(session,r);
		return response(r);
	}

	@Override
	public Response outCall(Operator operator, String opNumber,
			String callNumber) throws CtiServerException {
		Sessionable session = getSession(operator);
		OutCallRequest r = new OutCallRequest();
		r.setOpNumber(opNumber);
		r.setPhone(callNumber);
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public Response outCallCancel(Operator operator) throws CtiServerException {
		Sessionable session = getSession(operator);
		OutCallCancelRequest r = new OutCallCancelRequest();
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public Response mobileNumber(Operator operator, String number)
			throws CtiServerException {
		Sessionable session = getSession(operator);
		MobileNumberRequest r = new MobileNumberRequest();
		r.setNumber(number);
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public Response mobileNumberCancel(Operator operator)
			throws CtiServerException {
		Sessionable session = getSession(operator);
		MobileNumberCancelRequest r = new MobileNumberCancelRequest();
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public Response changePassword(Operator operator, String password)
			throws CtiServerException {
		Sessionable session = getSession(operator);
		PasswordRequest r = new PasswordRequest();
		r.setPassword(password);
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public Response resum(Operator operator) throws CtiServerException {
		Sessionable session = getSession(operator);
		ResumeRequest r = new ResumeRequest();
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public Response rest(Operator operator) throws CtiServerException {
		Sessionable session = getSession(operator);
		RestRequest r = new RestRequest();
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public Response silence(Operator operator) throws CtiServerException {
		Sessionable session = getSession(operator);
		SilenceRequest r = new SilenceRequest();
		r.setFlag("1");
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public Response closeSilence(Operator operator) throws CtiServerException {
		Sessionable session = getSession(operator);
		SilenceRequest r = new SilenceRequest();
		r.setFlag("0");
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public Response callHold(Operator operator) throws CtiServerException {
		Sessionable session = getSession(operator);
		CallHoldRequest r = new CallHoldRequest();
		r.setFlag("1");
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public Response closeCallHold(Operator operator) throws CtiServerException {
		Sessionable session = getSession(operator);
		CallHoldRequest r = new CallHoldRequest();
		r.setFlag("0");
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public Response callHelp(Operator operator, String workId, String number,
			String flag) throws CtiServerException {
		Sessionable session = getSession(operator);
		CallHelpRequest r = new CallHelpRequest();
		r.setTransferNumber(number);
		r.setTransferWorkId(workId);
		r.setStatus(flag);
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public Response transferGroup(Operator operator, String groupId)
			throws CtiServerException {
		Sessionable session = getSession(operator);
		TransferGroupRequest r = new TransferGroupRequest();
		r.setGroupId(groupId);
		send(session,r);
		return responseSingle(r);
	}

	@Override
	public Response transferOne(Operator operator, String workId, String number)
			throws CtiServerException {
		Sessionable session = getSession(operator);
		TransferOneRequest r = new TransferOneRequest();
		r.setWorkId(workId);
		r.setNumber(number);
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
	
	@SuppressWarnings("unchecked" )
	private List<Response> response(Requestable<? extends Response> request)throws CtiServerException{
		try{
			return (List<Response>)request.response();
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
	
	public void setHost(String host){
		_host = host;
	}
	
	public void setPort(int port){
		_port = port;
	}

}
