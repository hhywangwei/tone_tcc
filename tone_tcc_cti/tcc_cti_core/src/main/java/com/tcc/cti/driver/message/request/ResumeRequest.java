package com.tcc.cti.driver.message.request;

import static com.tcc.cti.driver.message.MessageType.Resume;

import com.tcc.cti.driver.message.response.Response;

/**
 * 恢复工作请求
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class ResumeRequest extends BaseRequest<Response> {

    public ResumeRequest() {
        super(Resume.request());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResumeRequest [_messageType=");
        builder.append(_messageType);
        builder.append(", _responses=");
        builder.append(_responses);
        builder.append("]");
        return builder.toString();
    }
}
