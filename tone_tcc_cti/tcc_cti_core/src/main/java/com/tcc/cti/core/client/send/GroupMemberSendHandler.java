package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.GroupMember;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.GroupMemberRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

/**
 * 发送获取组成员信息
 * 
 * <pre>消息格式如下:
 * {@literal <msg>worker_number_info</msg><seq>21</seq><CompanyID>11</CompanyID><GroupID>0</GroupID>}
 * 
 * msg:消息类型
 * seq:消息序号，通过该编号可使客户端发送消息和服务端返回信息关联
 * CompanyID:公司编号
 * GroupID:分组编号
 * 
 * {@code 是线程安全类}
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class GroupMemberSendHandler extends AbstractSendHandler{
	private static final String GROUP_ID_FORMAT = "<GroupID>%s</GroupID>";
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return 	GroupMember.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			OperatorKey key, StringBuilder builder) {
		
		GroupMemberRequest r = (GroupMemberRequest)request;
		buildOperator(key,builder);
		builder.append(String.format(GROUP_ID_FORMAT, r.getGroupId()));	
	}
}
