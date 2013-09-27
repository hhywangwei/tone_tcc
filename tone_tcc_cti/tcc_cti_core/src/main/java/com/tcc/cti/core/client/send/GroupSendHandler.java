package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.GroupInfo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.request.GroupRequest;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 实现获得分组信息发送
 *
 * <pre>消息格式如下:
 * {@literal <msg>group_info</msg><seq>2</seq><CompanyID>11</CompanyID><GroupID>0</GroupID>}
 * msg:消息类型
 * seq:消息序号，通过该编号可使客户端发送消息和服务端返回信息关联
 * CompanyID:公司编号
 * GroupID:分组编号 不指定分组编号查询该公司下所有分组信息 
 * 
 * {@code 线程安全类}
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class GroupSendHandler extends AbstractSendHandler{
	private static final Logger logger = LoggerFactory.getLogger(GroupSendHandler.class);
	private static final String GROUP_ID_FORMAT = "<GroupID>%s</GroupID>";
	
	@Override
	protected boolean isSend(RequestMessage message) {
		return message != null && 
				GroupInfo.requestType().equals(
						message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message, GeneratorSeq generator) {
		GroupRequest request = (GroupRequest)message;
		
		StringBuilder sb = new StringBuilder(512);
		sb.append(String.format(MSG_FORMAT, message.getMessageType()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(COMPANY_ID_FORMAT, request.getCompayId()));
		if(StringUtils.isNotBlank(request.getGroupId())){
			sb.append(String.format(GROUP_ID_FORMAT, request.getGroupId()));
		}
		String m = sb.toString();
		
		logger.debug("GroupInfo send message is {}",m);
		
		return m;
	}
}
