package com.tcc.cti.driver.message.request;

import static com.tcc.cti.driver.message.MessageType.Login;

import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.process.handler.send.LoginSendHandler;

/**
 * 请求登录信息对象
 *
 * <pre>
 * _opNumber:座席号
 * _password:用户密码使用（sh1）加密传输
 * _type:用户类型
 * </pre>
 *
 * 详细参考{@link LoginSendHandler}
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class LoginRequest extends BaseRequest<Response> {

    private String _opNumber;
    private String _password;
    private String _type;

    public LoginRequest() {
        super(Login.request());
    }

    public String getOpNumber() {
        return _opNumber;
    }

    public void setOpNumber(String opNumber) {
        this._opNumber = opNumber;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        this._password = password;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        this._type = type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LoginRequest [_opNumber=");
        builder.append(_opNumber);
        builder.append(", _password=");
        builder.append(_password);
        builder.append(", _type=");
        builder.append(_type);
        builder.append(", _messageType=");
        builder.append(_messageType);
        builder.append(", _responses=");
        builder.append(_responses);
        builder.append("]");
        return builder.toString();
    }
}
