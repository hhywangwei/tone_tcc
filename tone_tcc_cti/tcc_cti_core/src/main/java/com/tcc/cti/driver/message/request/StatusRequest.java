package com.tcc.cti.driver.message.request;

import static com.tcc.cti.driver.message.MessageType.Status;

import com.tcc.cti.driver.message.response.Response;

/**
 * 设置坐系状态
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class StatusRequest extends BaseRequest<Response> {

    private String _workId;
    private String _status;

    public StatusRequest() {
        super(Status.request());
    }

    public void setWorkId(String workId) {
        _workId = workId;
    }

    public String getWorkId() {
        return _workId;
    }

    public void setStatus(String status) {
        _status = status;
    }

    public String getStatus() {
        return _status;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StatusRequest [_workId=");
        builder.append(_workId);
        builder.append(", _status=");
        builder.append(_status);
        builder.append(", _messageType=");
        builder.append(_messageType);
        builder.append(", _responses=");
        builder.append(_responses);
        builder.append("]");
        return builder.toString();
    }
}
