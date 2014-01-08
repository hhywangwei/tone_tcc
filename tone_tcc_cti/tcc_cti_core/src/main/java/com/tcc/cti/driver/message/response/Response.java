package com.tcc.cti.driver.message.response;

import static com.tcc.cti.driver.common.ResultCode.InstanceCode;

/**
 * 接收消息
 *
 * <pre>
 * _seq:序号
 * _result:返回结果
 * _detail:返回结果描述
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 *
 */
public class Response {

    private static final String PREFIX_DETAIL_TEMPLATE = "return_code_%s";
    protected static final String SUCCESS_RESULT = "0";

    protected final String _seq;
    protected final String _result;

    public Response(String seq, String result) {
        _seq = seq;
        _result = result;
    }

    public String getResult() {
        return _result;
    }

    public String getSeq() {
        return _seq;
    }

    public String getDetail() {
        String key = String.format(PREFIX_DETAIL_TEMPLATE, _result);
        return InstanceCode.getDetail(key);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Response [_seq=");
        builder.append(_seq);
        builder.append(", _result=");
        builder.append(_result);
        builder.append("]");
        return builder.toString();
    }
}
