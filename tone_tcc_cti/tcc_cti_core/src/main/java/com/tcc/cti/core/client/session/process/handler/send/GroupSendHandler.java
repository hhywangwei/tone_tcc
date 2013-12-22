package com.tcc.cti.core.client.session.process.handler.send;

import static com.tcc.cti.core.message.MessageType.Group;
import static com.tcc.cti.core.message.MessageType.GroupMember;

import org.apache.commons.lang3.StringUtils;

import com.tcc.cti.core.client.OperatorKey;
import com.tcc.cti.core.message.request.GroupRequest;
import com.tcc.cti.core.message.request.Requestable;
import com.tcc.cti.core.message.response.Response;

/**
 * 实现获得分组信息发送
 *
 * <pre>消息格式如下:
 * {@literal <msg>group_info</msg><seq>2</seq><CompanyID>11</CompanyID><GroupID>0</GroupID>}
 * {@literal <msg>worker_number_info</msg><seq>21</seq><CompanyID>11</CompanyID><GroupID>0</GroupID>}
 * 
 * msg:消息类型
 * seq:消息序号，通过该编号可使客户端发送消息和服务端返回信息关联
 * CompanyID:公司编号
 * GroupID:分组编号 不指定分组编号查询该公司下所有分组信息 
 * 
 * {@code 线程安全类}
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class GroupSendHandler extends AbstractSendHandler{
	private static final String GROUP_ID_FORMAT = "<GroupID>%s</GroupID>";
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return 	Group.isRequest(request.getMessageType()) ||
				GroupMember.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			OperatorKey key, StringBuilder builder) {
		
		GroupRequest r = (GroupRequest)request;
		String companyId = String.format(COMPANY_ID_FORMAT, key.getCompanyId());
		builder.append(companyId);
		if(StringUtils.isNotBlank(r.getGroupId())){
			builder.append(String.format(GROUP_ID_FORMAT, r.getGroupId()));
		}
	}
}
