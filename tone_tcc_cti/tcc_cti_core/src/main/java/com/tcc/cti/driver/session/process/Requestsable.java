package com.tcc.cti.driver.session.process;

import com.tcc.cti.driver.message.event.RequestEvent;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.message.token.Tokenable;

/**
 * 请求消息池接口
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface Requestsable extends RequestEvent {

    /**
     * 接收消息
     * 
     * @param token 接收消息令牌
     * @param response  返回输出对象
     */
    void recevie(Tokenable token, Response response);
}
