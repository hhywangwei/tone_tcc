package com.tcc.cti.driver.message.request;

import static com.tcc.cti.driver.message.MessageType.Logout;

import com.tcc.cti.driver.message.response.Response;

public class LogoutRequest extends BaseRequest<Response> {

    public LogoutRequest() {
        super(Logout.request());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LogoutRequest [_messageType=");
        builder.append(_messageType);
        builder.append(", _responses=");
        builder.append(_responses);
        builder.append("]");
        return builder.toString();
    }
}
