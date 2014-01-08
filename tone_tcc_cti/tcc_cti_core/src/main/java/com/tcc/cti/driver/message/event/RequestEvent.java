package com.tcc.cti.driver.message.event;

import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.message.token.Tokenable;

/**
 * 请求消息事件，监控消息发送和接收消息完成回调处理
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public interface RequestEvent {

    /**
     * 发送命令前事件触发回调函数
     *
     * @param token 请求令牌
     * @param request 请求对象
     */
    void beforeSend(Tokenable token, Requestable<? extends Response> request);

    /**
     * 接收服务端消息完成回调函数
     *
     * @param token 请求令牌
     */
    void finishReceive(Tokenable token);
}
