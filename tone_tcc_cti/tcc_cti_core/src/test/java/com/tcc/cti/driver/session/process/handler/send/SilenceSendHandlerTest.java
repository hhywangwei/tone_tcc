package com.tcc.cti.driver.session.process.handler.send;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.BaseRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.request.SilenceRequest;
import com.tcc.cti.driver.message.response.CallResponse;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;

/**
 * 单元测试{@link SilenceSendHandler}
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class SilenceSendHandlerTest {

    @Test
    public void testIsSend() {
        SilenceSendHandler handler = new SilenceSendHandler();

        Requestable<? extends Response> not = new BaseRequest<Response>("not");
        Assert.assertFalse(handler.isSend(not));

        SilenceRequest r = new SilenceRequest();
        Assert.assertTrue(handler.isSend(r));
    }

    @Test
    public void testBuildMessage() {
        SilenceRequest request = initRequest();

        SilenceSendHandler handler = new SilenceSendHandler();
        StringBuilder builder = new StringBuilder();
        String e = "<CompanyID>1</CompanyID><OPID>8001</OPID><CallLeg>12-33</CallLeg><Flag>1</Flag>";
        Operator key = new Operator("1", "8001");
        Phone phone = initPhone();
        handler.buildMessage(phone, request, key, builder);
        Assert.assertEquals(e, builder.toString());
    }

    private Phone initPhone() {
        Phone phone = new Phone();
        CallResponse r = new CallResponse.Builder("0")
            .setCallLeg("12-33").build();
        phone.calling(r);

        return phone;
    }

    private SilenceRequest initRequest() {
        SilenceRequest request = new SilenceRequest();
        request.setFlag("1");

        return request;
    }
}
