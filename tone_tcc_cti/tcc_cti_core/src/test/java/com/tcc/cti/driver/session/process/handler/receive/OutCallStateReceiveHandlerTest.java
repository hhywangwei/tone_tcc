package com.tcc.cti.driver.session.process.handler.receive;

import static com.tcc.cti.driver.message.MessageType.OutCallState;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.message.response.OutCallStateResponse;

/**
 * {@link OutCallStateReceiveHandler}单元测试
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OutCallStateReceiveHandlerTest {

    @Test
    public void testIsReceive() {
        OutCallStateReceiveHandler handler = new OutCallStateReceiveHandler();

        Assert.assertFalse(handler.isReceive(null));
        Assert.assertFalse(handler.isReceive("not"));
        Assert.assertTrue(handler.isReceive(OutCallState.response()));
    }

    @Test
    public void testBuildMessage() {
        Map<String, String> content = initContent();

        OutCallStateReceiveHandler handler = new OutCallStateReceiveHandler();

        OutCallStateResponse r = (OutCallStateResponse) handler.buildMessage("1", "2", "3", content);
        Assert.assertEquals("3", r.getSeq());
        Assert.assertEquals("101", r.getCallLeg());
        Assert.assertEquals("1", r.getState());
    }

    private Map<String, String> initContent() {
        Map<String, String> content = new HashMap<String, String>();

        content.put("CallLeg", "101");
        content.put("State", "1");

        return content;
    }
}
