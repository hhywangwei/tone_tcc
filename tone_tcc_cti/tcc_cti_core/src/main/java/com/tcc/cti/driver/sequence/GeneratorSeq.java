package com.tcc.cti.driver.sequence;

/**
 * 生成发送消息序列号类
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface GeneratorSeq {

    /**
     * 生成序号
     *
     * @return
     */
    String next();

}
