package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.Login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.common.PasswordUtils;
import com.tcc.cti.core.message.request.LoginRequest;
import com.tcc.cti.core.message.request.RequestMessage;

/**
 * 实现发送登录cti消息
 * 
 * <pre>消息格式如下:
 * {@literal <msg>login</msg><seq>2</seq><Type>1</Type><CompanyID>1</CompanyID><OPID>8001</OPID><OPNumber>8002</OPNumber><PassWord>md5加密</PassWord><AutoLogin>0</AutoLogin>}
 * msg:消息类型
 * seq:消息序号，通过该编号可使客户端发送消息和服务端返回信息关联
 * type:座席身份，用数字代表。1－座席、2－班长、3－专家
 * CompanyID：企业编号
 * OPID：工号
 * OPNumber：座席号
 * PassWord：登录密码 32位
 * AutoLogin：？？？
 * 
 * {@code 是线程安全类}
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */

public class LoginSendHandler extends AbstractSendHandler{
	private static final Logger logger = LoggerFactory.getLogger(LoginSendHandler.class);
	 
	private static final String TYPE_FORMAT = "<Type>%s</Type>";
	private static final String OPNUMBER_FORMAT = "<OPNumber>%s</OPNumber>";
	private static final String PASSWORD_FORMAT = "<PassWord>%s</PassWord>";
	
	@Override
	protected boolean isSend(RequestMessage message) {
		return Login.isRequest(message.getMessageType());
	}

	@Override
	protected String buildMessage(RequestMessage message,GeneratorSeq generator){
		LoginRequest request = (LoginRequest)message;
		
		StringBuilder sb = new StringBuilder(128);
		sb.append(String.format(MSG_FORMAT, request.getMessageType()));
		sb.append(String.format(SEQ_FORMAT, generator.next()));
		sb.append(String.format(TYPE_FORMAT, request.getType()));
		sb.append(String.format(COMPANY_ID_FORMAT,request.getCompayId()));
		sb.append(String.format(OPID_FORMAT, request.getOpId()));
		sb.append(String.format(OPNUMBER_FORMAT, request.getOpNumber()));
		String password = PasswordUtils.encodeMD5(request.getPassword());
		sb.append(String.format(PASSWORD_FORMAT, password));
		
		String m = sb.toString();
		logger.debug("Build message is {}",m);
		return m;
	}
	
}
