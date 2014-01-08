package com.tcc.cti.driver.session.process.handler.receive;

/**
 * 接收消息解析异常
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class ParseMessageException extends Exception {

    private static final long serialVersionUID = 1L;

    public ParseMessageException(Throwable t) {
        super(t);
    }

}
