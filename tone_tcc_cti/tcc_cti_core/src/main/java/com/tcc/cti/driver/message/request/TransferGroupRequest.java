package com.tcc.cti.driver.message.request;

import static com.tcc.cti.driver.message.MessageType.TransferGroup;

import com.tcc.cti.driver.message.response.Response;

/**
 * 电话转到组请求
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class TransferGroupRequest extends BaseRequest<Response> {

    private String _groupId;

    public TransferGroupRequest() {
        super(TransferGroup.request());
    }

    public void setGroupId(String groupId) {
        _groupId = groupId;
    }

    public String getGroupId() {
        return _groupId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TransferGroupRequest [_groupId=");
        builder.append(_groupId);
        builder.append(", _messageType=");
        builder.append(_messageType);
        builder.append(", _responses=");
        builder.append(_responses);
        builder.append("]");
        return builder.toString();
    }
}
