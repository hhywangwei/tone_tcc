package com.tcc.cti.driver.session.process.handler.receive;

import static com.tcc.cti.driver.message.MessageType.Monitor;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.message.response.MonitorResponse;

/**
 * {@link MonitorReceiveHandler}单元厕所
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class MonitorReceiveHandlerTest {

    @Test
    public void testIsReceive() {
        MonitorReceiveHandler handler = new MonitorReceiveHandler();

        Assert.assertFalse(handler.isReceive(null));
        Assert.assertFalse(handler.isReceive("not"));
        Assert.assertTrue(handler.isReceive(Monitor.response()));
    }

    @Test
    public void testRequestMessageType() {
        MonitorReceiveHandler handler = new MonitorReceiveHandler();
        Monitor.isRequest(handler.getRequestMessageType(null));
    }

    @Test
    public void testBuildMessage() {
        Map<String, String> content = initContent();

        MonitorReceiveHandler handler = new MonitorReceiveHandler();
        MonitorResponse message = (MonitorResponse) handler.buildMessage("1", "2", "3", content);

        Assert.assertEquals("3", message.getSeq());
        Assert.assertEquals("0005", message.getWorkId());
        Assert.assertEquals("0005", message.getName());
        Assert.assertEquals("3", message.getGroupAttribute());
        Assert.assertEquals("1,2", message.getGroupString());
        Assert.assertEquals("8622", message.getNumber());
        Assert.assertEquals("1", message.getLoginState());
        Assert.assertEquals("0", message.getMobileState());
        Assert.assertEquals("1", message.getState());
        Assert.assertEquals("1", message.getBindState());
        Assert.assertEquals("1", message.getCallState());
    }

    private Map<String, String> initContent() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("WorkID", "0005");
        map.put("Name", "0005");
        map.put("GroupAttribute", "3");
        map.put("GroupString", "1,2");
        map.put("Number", "8622");
        map.put("LoginState", "1");
        map.put("MobileState", "0");
        map.put("State", "1");
        map.put("BindState", "1");
        map.put("CallState", "1");
        map.put("MobileNumber", "1234567");

        return map;
    }
}
