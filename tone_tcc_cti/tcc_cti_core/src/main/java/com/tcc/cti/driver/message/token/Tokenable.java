package com.tcc.cti.driver.message.token;

/**
 * 请求消息令牌接口，在异步程序中使请求处理的请求得到相应的返回值
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 *
 */
public interface Tokenable {

    /**
     * 消息令牌，每个消息需要保持唯一值
     *
     * @return 令牌值
     */
    Object token();
}
