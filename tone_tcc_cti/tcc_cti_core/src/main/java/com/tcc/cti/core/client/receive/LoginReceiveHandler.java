package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Login;

import java.util.Map;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.client.OperatorChannel;
import com.tcc.cti.core.client.monitor.HeartbeatKeepable;
import com.tcc.cti.core.client.monitor.NoneHeartbeatKeep;
import com.tcc.cti.core.message.pool.CtiMessagePool;

/**
 * 接受登陆返回信息，登录成功开始发送心跳消息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class LoginReceiveHandler extends AbstractReceiveHandler{
	private static final String RESULT_PARAMETER = "result";
	private HeartbeatKeepable _heartbeat = new NoneHeartbeatKeep();
	
	@Override
	protected boolean isReceive(String msgType) {
		return msgType.equals(Login.getType());
	}

	@Override
	protected void receiveHandler(CtiMessagePool pool,
			OperatorChannel channel, Map<String, String> content) throws ClientException {
		String result = content.get(RESULT_PARAMETER);
		if(loginSuccess(result)){
			if(_heartbeat.register(channel)){
				//TODO 发送到消息池
			}
		}else{
			//TODO 发送到消息池
		}
	}
	
	private boolean loginSuccess(String result){
		return result.equals("0");
	}
	
	public void setHeartbeatKeep(HeartbeatKeepable heartbeat){
		_heartbeat = heartbeat;
	}
}
