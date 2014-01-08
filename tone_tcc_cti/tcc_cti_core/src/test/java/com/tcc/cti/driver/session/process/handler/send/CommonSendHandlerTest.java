package com.tcc.cti.driver.session.process.handler.send;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.BaseRequest;
import com.tcc.cti.driver.message.request.CallRequest;
import com.tcc.cti.driver.message.request.LogoutRequest;
import com.tcc.cti.driver.message.request.MobileNumberCancelRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.request.RestRequest;
import com.tcc.cti.driver.message.request.ResumeRequest;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.sequence.GeneratorSeq;
import com.tcc.cti.driver.session.Phone;

/**
 * {@link CommonSendHandler}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class CommonSendHandlerTest {

    @Test
    public void testIsSend() {
        CommonSendHandler handler = new CommonSendHandler();

        Requestable<? extends Response> not = new BaseRequest<Response>("not");
        Assert.assertFalse(handler.isSend(not));

        Requestable<? extends Response> r = new LogoutRequest();
        Assert.assertTrue(handler.isSend(r));

        r = new MobileNumberCancelRequest();
        Assert.assertTrue(handler.isSend(r));

        r = new RestRequest();
        Assert.assertTrue(handler.isSend(r));

        r = new ResumeRequest();
        Assert.assertTrue(handler.isSend(r));
    }

    @Test
    public void testBuildMessage() {
        CallRequest request = new CallRequest();

        GeneratorSeq generator = mock(GeneratorSeq.class);
        when(generator.next()).thenReturn("1");

        CommonSendHandler handler = new CommonSendHandler();
        Operator key = new Operator("1", "2");
        StringBuilder builder = new StringBuilder();
        handler.buildMessage(new Phone(), request, key, builder);
        String e = "<CompanyID>1</CompanyID><OPID>2</OPID>";
        Assert.assertEquals(e, builder.toString());
    }
}
