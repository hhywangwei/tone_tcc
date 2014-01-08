package com.tcc.cti.driver.message.request;

import static com.tcc.cti.driver.message.MessageType.CallHelp;

import com.tcc.cti.driver.message.response.Response;

/**
 * 求助需求对象
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class CallHelpRequest extends BaseRequest<Response> {

    private String _transferWorkId;
    private String _transferNumber;
    private String _status;

    public CallHelpRequest() {
        super(CallHelp.request());
    }

    public void setTransferWorkId(String transferWorkID) {
        _transferWorkId = transferWorkID;
    }

    public String getTransferWorkId() {
        return _transferWorkId;
    }

    public void setTransferNumber(String number) {
        _transferNumber = number;
    }

    public String getTransferNumber() {
        return _transferNumber;
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
        builder.append("CallHelpRequest [_transferWorkId=");
        builder.append(", _transferWorkId=");
        builder.append(_transferWorkId);
        builder.append(", _transferNumber=");
        builder.append(_transferNumber);
        builder.append(", status=");
        builder.append(_status);
        builder.append(", _messageType=");
        builder.append(_messageType);
        builder.append(", _responses=");
        builder.append(_responses);
        builder.append("]");
        return builder.toString();
    }
}
