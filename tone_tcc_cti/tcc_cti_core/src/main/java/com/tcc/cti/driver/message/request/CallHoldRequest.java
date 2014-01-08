package com.tcc.cti.driver.message.request;

import static com.tcc.cti.driver.message.MessageType.CallHold;

import com.tcc.cti.driver.message.response.Response;

/**
 * 保存通话请求对象
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class CallHoldRequest extends BaseRequest<Response> {

    private String _flag;

    public CallHoldRequest() {
        super(CallHold.request());
    }

    public void setFlag(String flag) {
        _flag = flag;
    }

    public String getFlag() {
        return _flag;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CallHoldRequest [_flag=");
        builder.append(_flag);
        builder.append(", _messageType=");
        builder.append(_messageType);
        builder.append(", _responses=");
        builder.append(_responses);
        builder.append("]");
        return builder.toString();
    }
}
