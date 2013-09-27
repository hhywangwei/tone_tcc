package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.ObtainMember;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.GroupMemberRequest;
import com.tcc.cti.core.message.request.RequestMessage;

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
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class GroupMemberSendHandler extends AbstractSendHandler{
	private static final Logger logger = LoggerFactory.getLogger(GroupMemberSendHandler.class);
	private static final String GROUP_ID_FORMAT = "<GroupID>%s</GroupID>";
	
	@Override
	protected boolean isSend(RequestMessage message) {
		return message != null && 
				ObtainMember.requestType().equals(
						message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message, GeneratorSeq generator) {
		GroupMemberRequest request = (GroupMemberRequest)message;
		
		StringBuilder sb = new StringBuilder(128);
		sb.append(String.format(MSG_FORMAT, request.getMessageType()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(COMPANY_ID_FORMAT, request.getCompayId()));
		sb.append(String.format(GROUP_ID_FORMAT, request.getGroupId()));	
		
		String m = sb.toString();
		logger.debug("Obtain message send is {}", m);
		
		return m;
	}

}
