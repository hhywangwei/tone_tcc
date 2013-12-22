package com.tcc.cti.core.client.receive;

import static com.tcc.cti.core.message.MessageType.Group;

import java.util.Map;

import com.tcc.cti.core.message.response.GroupResponse;
import com.tcc.cti.core.message.response.Response;

/**
 * 接受分组信息
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class GroupReceiveHandler extends AbstractReceiveHandler{
	private static final String GROUP_ID_PARAMETER = "GroupID";
	private static final String GROUP_NAME_PARAMETER = "GroupName";
	private static final String MAX_QUEUE_PARAMETER = "maxQueue";
	private static final String GROUP_WORK_STATE_PARAMETER = "Group_WorkState";
	private static final String CHOOSE_OP_TYPE_PARAMETER = "ChooseOPType";
	
	@Override
	protected boolean isReceive(String msgType) {
		return Group.isResponse(msgType);
	}

	@Override
	protected String getRequestMessageType(String msgType) {
		return Group.request();
	}
	
	@Override
	protected Response buildMessage(String companyId, String opId,
			String seq, Map<String, String> content) {
		
		return new GroupResponse.Builder(seq).
				setGroupId(content.get(GROUP_ID_PARAMETER)).
				setGroupName(content.get(GROUP_NAME_PARAMETER)).
				setMaxQueue(content.get(MAX_QUEUE_PARAMETER)).
				setGroupWorkState(content.get(GROUP_WORK_STATE_PARAMETER)).
				setChooseOpType(content.get(CHOOSE_OP_TYPE_PARAMETER)).
				build();
	}
}
