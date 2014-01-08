package com.tcc.cti.driver.message.request;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.message.response.Response;

/**
 * {@link  MultiBaseRequest}单元测试
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class MultiBaseRequestTest {

    @Test
    public void testReceiveComplete() {
        Response response = new Response("-1", "1");
        MultiBaseRequest<Response> request = new MultiBaseRequest<Response>("group");
        request.receive(response);
        Assert.assertTrue(request._complete);
    }

    @Test
    public void testReceiveNotComplete() {
        Response response = new Response("1", "1");
        MultiBaseRequest<Response> request = new MultiBaseRequest<Response>("group");
        request.receive(response);
        Assert.assertFalse(request._complete);
    }
}
