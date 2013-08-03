package com.tcc.cti.core.client.send;

import static com.tcc.cti.core.message.MessageType.*;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.message.CtiMessage;
import com.tcc.cti.core.message.Login;

/**
 * 实现发送登录cti消息
 * 
 * <pre>消息格式如下:
 * {@literal <head>00185</head><msg>login</msg><seq>2</seq><Type>1</Type><CompanyID>1</CompanyID><OPID>8001</OPID><OPNumber>8002</OPNumber><PassWord>md5加密</PassWord><AutoLogin>0</AutoLogin>}
 * <pre>消息格式说明
 * head:消息头，表示消息的长度，为五位固定长字符串，未满5位用“0”替补
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
	
	private static final String DEFAULT_MESSAGE_LENGTH = "00000";
	private static final String HEAD_FORMAT = "<head>%s</head>";
	private static final String MSG_FORMAT = "<msg>%s</msg>";
	private static final String SEQ_FORMAT = "<seq>%s</seq>"; 
	private static final String TYPE_FORMAT = "<Type>%s</Type>";
	private static final String COMPANY_ID_FORMAT = "<CompanyID>%s</CompanyID>";
	private static final String OPID_FORMAT = "<OPID>%s</OPID>";
	private static final String OPNUMBER_FORMAT = "<OPNumber>%s</OPNumber>";
	private static final String PASSWORD_FORMAT = "<PassWord>%s</PassWord>";
	
	@Override
	protected boolean isSend(CtiMessage message) {
		if(message == null){
			return false;
		}
		return message.getClass().equals(Login.class);
	}

	@Override
	protected byte[] getMessage(
			CtiMessage message,GeneratorSeq generator,String charset) {
		
		String seq = generator.next();
		String m = buildMessage((Login)message,seq);
	    return headCompletion(m,charset);
	}
	
	private String buildMessage(Login login,String seq){
		StringBuilder sb = new StringBuilder(128);
		sb.append(String.format(MSG_FORMAT, Login.getType()));
		sb.append(String.format(SEQ_FORMAT, seq));
		sb.append(String.format(TYPE_FORMAT, login.getType()));
		sb.append(String.format(COMPANY_ID_FORMAT,login.getCompayId()));
		sb.append(String.format(OPID_FORMAT, login.getOpId()));
		sb.append(String.format(OPNUMBER_FORMAT, login.getOpNumber()));
		//TODO 密码需要解密
		sb.append(String.format(PASSWORD_FORMAT, login.getPassword()));
		
		String m = sb.toString();
		logger.debug("Build message is {}",m);
		return m;
	}
	
    private byte[] headCompletion (String message,String charset){
    	Charset c = Charset.forName(charset);
    	byte[] bytes = message.getBytes(c);
    	int length = bytes.length;
    	if(length > MESSAGE_MAX_LENGTH){
    		logger.error("Login message is {},but upper limit {}",
    				length,MESSAGE_MAX_LENGTH);
    		throw new IllegalStateException("Login message is too length");
    	}
    	
    	String m = getHeadSegment(length) + message;
    	return m.getBytes(c);
	}
    
    private String getHeadSegment(int length){
    	String ds = DEFAULT_MESSAGE_LENGTH;
    	String rs = String.valueOf(length);
    	String ls = ds.substring(0,ds.length() - rs.length()) + rs;
    	return String.format(HEAD_FORMAT, ls);
    }
}
