package com.tcc.cti.driver.session.process;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.message.token.Tokenable;

/**
 * 请求消息池，等待服务器返回，消息通过令牌匹配
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class Requests implements Requestsable {

    private static final Logger logger = LoggerFactory.getLogger(Requests.class);

    private static final int DEFAULT_SIZE = 100;

    private final Map<Object, Requestable<? extends Response>> requests
        = new ConcurrentHashMap<Object, Requestable<? extends Response>>(DEFAULT_SIZE);

    @Override
    public void beforeSend(Tokenable token, Requestable<? extends Response> request) {
        Requestable<? extends Response> o = requests.put(token.token(), request);
        if (o != null) {
            logger.error("Request key {} is exist,Request is {}.", token.token(), request.toString());
        }
    }

    @Override
    public void finishReceive(Tokenable token) {
        Requestable<? extends Response> o = requests.remove(token.token());
        if (o == null) {
            logger.error("Request key {} not exist");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void recevie(Tokenable token, Response response) {
        Requestable<Response> o = (Requestable<Response>) requests.get(token.token());
        if (o == null) {
            logger.error("Request key {} not exist");
            return;
        }
        o.receive(response);
    }
}
