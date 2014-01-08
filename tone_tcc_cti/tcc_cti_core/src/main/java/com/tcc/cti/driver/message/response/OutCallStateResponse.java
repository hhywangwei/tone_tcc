package com.tcc.cti.driver.message.response;

import static com.tcc.cti.driver.common.ResultCode.InstanceCode;

/**
 * 外呼状态返回
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallStateResponse extends Response {

    private static final String PREFIX_DETAIL_TEMPLATE = "out_call_%s";

    private final String _callLeg;
    private final String _state;

    public OutCallStateResponse(String seq, String callLeg, String state) {
        super(seq, SUCCESS_RESULT);
        _callLeg = callLeg;
        _state = state;
    }

    public String getCallLeg() {
        return _callLeg;
    }

    public String getState() {
        return _state;
    }

    @Override
    public String getDetail() {
        String key = String.format(PREFIX_DETAIL_TEMPLATE, _result);
        return InstanceCode.getDetail(key);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OutCallStateResponse [_callLeg=");
        builder.append(_callLeg);
        builder.append(", _state=");
        builder.append(_state);
        builder.append(", _seq=");
        builder.append(_seq);
        builder.append("]");
        return builder.toString();
    }
}
