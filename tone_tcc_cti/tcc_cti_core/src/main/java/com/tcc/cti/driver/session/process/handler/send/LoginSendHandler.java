package com.tcc.cti.driver.session.process.handler.send;

import static com.tcc.cti.driver.message.MessageType.Login;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.common.PasswordUtils;
import com.tcc.cti.driver.message.request.LoginRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;

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
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */

public class LoginSendHandler extends AbstractSendHandler{
	private static final String TYPE_FORMAT = "<Type>%s</Type>";
	private static final String OPNUMBER_FORMAT = "<OPNumber>%s</OPNumber>";
	private static final String PASSWORD_FORMAT = "<PassWord>%s</PassWord>";
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return Login.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Phone phone,Requestable<? extends Response> request,
			Operator key, StringBuilder builder){
		
		LoginRequest r = (LoginRequest)request;
		buildOperator(key,builder);
		builder.append(String.format(TYPE_FORMAT, r.getType()));
		builder.append(String.format(OPNUMBER_FORMAT, r.getOpNumber()));
		String password = PasswordUtils.encodeMD5(r.getPassword());
		builder.append(String.format(PASSWORD_FORMAT, password));
	}
	
}
