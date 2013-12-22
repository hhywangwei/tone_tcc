package com.tcc.cti.driver.session.process.handler.receive;

import static com.tcc.cti.driver.message.MessageType.Login;

import java.util.Map;

import com.tcc.cti.driver.session.Sessionable;
import com.tcc.cti.driver.session.process.Requestsable;

/**
 * 接受登陆返回信息，登录成功开始发送心跳消息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class LoginReceiveHandler extends AbstractReceiveHandler{
	private static final String RESULT_PARAMETER = "result";
	private static final String LOGIN_SUCCESS = "0";
	
	@Override
	protected boolean isReceive(String msgType) {
		return Login.isResponse(msgType);
	}
	
	@Override
	protected void receiveHandler(Requestsable requests,Sessionable session,
			String msgType,Map<String, String> content) {
		
		String result = content.get(RESULT_PARAMETER);
		boolean success = loginSuccess(result);
		session.login(success);
		super.receiveHandler(requests, session,msgType, content);
	}
	
	private boolean loginSuccess(String result){
		return result.equals(LOGIN_SUCCESS);
	}
}
