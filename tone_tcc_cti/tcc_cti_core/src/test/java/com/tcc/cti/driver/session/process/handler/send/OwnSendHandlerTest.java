package com.tcc.cti.driver.session.process.handler.send;

import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.message.request.BaseRequest;
import com.tcc.cti.driver.message.request.OwnRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.session.Phone;

/**
 * 单元测试{@link SelfInfoSendHanlder}
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OwnSendHandlerTest {

    @Test
    public void testIsSend() {
        OwnSendHandler handler = new OwnSendHandler();

        Requestable<? extends Response> not = new BaseRequest<Response>("not");
        Assert.assertFalse(handler.isSend(not));

        OwnRequest m = new OwnRequest();
        Assert.assertTrue(handler.isSend(m));
    }

    @Test
    public void testBuildMessageIsWorkIdNull() {
        OwnRequest m = new OwnRequest();

        OwnSendHandler handler = new OwnSendHandler();
        Operator key = new Operator("1", "1");
        StringBuilder builder = new StringBuilder();
        Phone phone = new Phone();
        handler.buildMessage(phone, m, key, builder);
        String msg = "<WorkID></WorkID>";
        Assert.assertEquals(msg, builder.toString());
    }

    @Test
    public void testBuildMessage() {
        OwnRequest m = new OwnRequest();
        m.setWorkId("222");

        OwnSendHandler handler = new OwnSendHandler();
        Operator key = new Operator("1", "1");
        StringBuilder builder = new StringBuilder();
        Phone phone = new Phone();
        handler.buildMessage(phone, m, key, builder);
        String msg = "<WorkID>222</WorkID>";
        Assert.assertEquals(msg, builder.toString());
    }
}
