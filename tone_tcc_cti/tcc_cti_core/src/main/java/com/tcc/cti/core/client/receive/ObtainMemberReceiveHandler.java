package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.ObtainMember;

import java.util.Map;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.client.OperatorChannel;
import com.tcc.cti.core.message.pool.CtiMessagePool;

/**
 * 获得组成员信息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class ObtainMemberReceiveHandler extends AbstractReceiveHandler{

	@Override
	protected boolean isReceive(String msgType) {
		return ObtainMember.responseType().equals(msgType);
	}

	@Override
	protected void receiveHandler(CtiMessagePool pool, OperatorChannel channel,
			Map<String, String> content) throws ClientException {
		// TODO Auto-generated method stub
		
	}

}
