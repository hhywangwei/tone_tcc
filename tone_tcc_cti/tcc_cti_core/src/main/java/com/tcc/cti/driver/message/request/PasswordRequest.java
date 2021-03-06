package com.tcc.cti.driver.message.request;

import static com.tcc.cti.driver.message.MessageType.Password;

import com.tcc.cti.driver.common.PasswordUtils;
import com.tcc.cti.driver.message.response.Response;

/**
 * 请求修改密码对象
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class PasswordRequest extends BaseRequest<Response> {

    private String _password;

    public PasswordRequest() {
        super(Password.request());
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        _password = PasswordUtils.encodeMD5(password);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PasswordRequest [_password=");
        builder.append(_password);
        builder.append(", _messageType=");
        builder.append(_messageType);
        builder.append(", _responses=");
        builder.append(_responses);
        builder.append("]");
        return builder.toString();
    }
}
