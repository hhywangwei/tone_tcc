package com.tcc.cti.driver.session.process.handler.send;

import static com.tcc.cti.driver.message.MessageType.Own;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;

/**
 * 发送读取本座席信息
 * 
 * <pre>消息格式如下:
 * {@literal <msg>per_worker_info</msg><seq>4</seq><WorkID></WorkID>}
 * msg:消息类型
 * seq:消息序号，通过该编号可使客户端发送消息和服务端返回信息关联
 * WorkID为空表示查座席本人信息，不为空表示查指定座席信息
 * 
 * {@code 是线程安全类}
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OwnSendHandler extends AbstractSendHandler{
	private static final String WORK_ID_FORMAT = "<WorkID></WorkID>";
	
	@Override
	protected boolean isSend(Requestable<? extends Response> request) {
		return Own.isRequest(request.getMessageType());
	}

	@Override
	protected void buildMessage(Requestable<? extends Response> request,
			Operator key, StringBuilder builder) {
		
		builder.append(WORK_ID_FORMAT);
	}
}
