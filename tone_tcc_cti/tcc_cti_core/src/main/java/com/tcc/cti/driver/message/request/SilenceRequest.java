package com.tcc.cti.driver.message.request;

import static com.tcc.cti.driver.message.MessageType.Silence;

import com.tcc.cti.driver.message.response.Response;

/**
 * 静音请求
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class SilenceRequest extends BaseRequest<Response> {

    private String _flag;

    public SilenceRequest() {
        super(Silence.request());
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
        builder.append("SilenceRequest [_flag=");
        builder.append(", _flag=");
        builder.append(_flag);
        builder.append(", _messageType=");
        builder.append(_messageType);
        builder.append(", _responses=");
        builder.append(_responses);
        builder.append("]");
        return builder.toString();
    }
}
