package com.tcc.cti.driver.session.process.handler.receive;

import static com.tcc.cti.driver.message.MessageType.GroupMember;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.driver.message.response.GroupMemberResponse;

/**
 * 单元测试{@link ObtainMemberHandler}
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class GroupMemberReceiveHandlerTest {

    @Test
    public void testIsReceive() {
        GroupMemberReceiveHandler handler = new GroupMemberReceiveHandler();

        Assert.assertFalse(handler.isReceive(null));
        Assert.assertFalse(handler.isReceive("ddd"));
        Assert.assertTrue(handler.isReceive(GroupMember.response()));
    }

    @Test
    public void testRequestMessageType() {
        GroupMemberReceiveHandler handler = new GroupMemberReceiveHandler();
        GroupMember.isRequest(handler.getRequestMessageType(null));
    }

    @Test
    public void testBuildMessage() {
        GroupMemberReceiveHandler handler = new GroupMemberReceiveHandler();
        Map<String, String> content = initContent();

        String companyId = "1";
        String opId = "1";
        String seq = "4";

        GroupMemberResponse message = handler.buildMessage(
            companyId, opId, seq, content);

        Assert.assertEquals("4", message.getSeq());
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
        Assert.assertEquals("1234567", message.getMobileNumber());
        Assert.assertEquals("0", message.getRecordFlag());
        Assert.assertEquals("0", message.getRecordingNow());
        Assert.assertEquals("0", message.getWorkModel());

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
        map.put("RecordFlag", "0");
        map.put("RecordingNow", "0");
        map.put("WorkModel", "0");

        return map;
    }

}
