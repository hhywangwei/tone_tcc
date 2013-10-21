package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Login;

import java.util.Map;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.client.OperatorChannel;
import com.tcc.cti.core.client.monitor.HeartbeatKeepable;
import com.tcc.cti.core.client.monitor.ScheduledHeartbeatKeep;
import com.tcc.cti.core.message.pool.CtiMessagePool;
import com.tcc.cti.core.message.response.LoginResponse;
import com.tcc.cti.core.message.response.ResponseMessage;

/**
 * 接受登陆返回信息，登录成功开始发送心跳消息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class LoginReceiveHandler extends AbstractReceiveHandler{
	private static final String RESULT_PARAMETER = "result";
	private static final String LOGIN_SUCCESS = "0";
	private HeartbeatKeepable _heartbeat = new ScheduledHeartbeatKeep();
	
	@Override
	protected boolean isReceive(String msgType) {
		return Login.isResponse(msgType);
	}

	@Override
	protected void receiveHandler(CtiMessagePool pool,
			OperatorChannel channel, Map<String, String> content) throws ClientException {
		
		String result = content.get(RESULT_PARAMETER);
		if(loginSuccess(result)){
			boolean success= _heartbeat.register(channel);
			if(!success){
				return ;
			}
		} 
		
		super.receiveHandler(pool, channel, content);
	}
	
	private boolean loginSuccess(String result){
		return result.equals(LOGIN_SUCCESS);
	}
	
	@Override
	protected ResponseMessage buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		
		String result = content.get(RESULT_PARAMETER);
		return new LoginResponse(companyId,opId,seq,result);
	}
	
	public void setHeartbeatKeep(HeartbeatKeepable heartbeat){
		_heartbeat = heartbeat;
	}
}
