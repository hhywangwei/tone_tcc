package com.tcc.cti.driver.buffer;

import org.junit.Assert;
import org.junit.Test;

import com.tcc.cti.driver.buffer.ByteMessageBuffer;

/**
 * 单元测试{@link ByteMessageBuffer}
 *
 * @author <a href="hhywangwei@gmail.com">wangewei</a>
 */
public class ByteMessageBufferTest {

    private final String charset = "ISO-8859-1";

    @Test
    public void testAppend() {
        ByteMessageBuffer buffer = new ByteMessageBuffer(100, charset);
        byte[] bytes = "abcd123456".getBytes();
        buffer.append(bytes);
        Assert.assertEquals(0, buffer.getPosition());
        Assert.assertEquals(10, buffer.getLimit());

        bytes = "efg".getBytes();
        buffer.append(bytes);
        Assert.assertEquals(0, buffer.getPosition());
        Assert.assertEquals(13, buffer.getLimit());
    }

    @Test
    public void testNext() throws Exception {
        String mes = "<head>00005</head>12345<head>";
        ByteMessageBuffer buffer = new ByteMessageBuffer(100, charset);
        buffer.append(mes.getBytes());

        String m = buffer.next();
        Assert.assertEquals("<head>00005</head>12345", m);

        mes = "00002</head>678";
        buffer.append(mes.getBytes());

        m = buffer.next();
        Assert.assertEquals("<head>00002</head>67", m);
    }

    @Test
    public void testNextNotHead() throws Exception {
        String mes = "dddddddd<head>00005</head>12345<head>";
        ByteMessageBuffer buffer = new ByteMessageBuffer(100, charset);
        buffer.append(mes.getBytes());

        String m = buffer.next();
        Assert.assertEquals("<head>00005</head>12345", m);
    }

    @Test
    public void testInputOverFullAppend() {
        final ByteMessageBuffer buffer = new ByteMessageBuffer(22, charset);
        String mes = "<head>00005</head>12345";
        buffer.append(mes.getBytes());
        Assert.assertEquals(0, buffer.getLimit());
    }

    @Test
    public void testBufferOverFullAppend() throws InterruptedException {
        final ByteMessageBuffer buffer = new ByteMessageBuffer(40, charset);
        String mes = "<head>00005</head>12345";
        buffer.append(mes.getBytes());
        mes = "<head>00015</head>123456789abcdef";
        buffer.append(mes.getBytes());
        String m = buffer.next();
        Assert.assertEquals(mes, m);
    }

    @Test
    public void testBufferEmpt() {
        final ByteMessageBuffer buffer = new ByteMessageBuffer(40, charset);
        String m = buffer.next();
        Assert.assertNull(m);
    }

    @Test
    public void testBufferNotCompletionNext() {
        final ByteMessageBuffer buffer = new ByteMessageBuffer(40, charset);
        String mes = "<head>00005</head>12";
        buffer.append(mes.getBytes());
        String m = buffer.next();
        Assert.assertNull(m);
        buffer.append("345".getBytes());
        m = buffer.next();
        Assert.assertEquals("<head>00005</head>12345", m);
    }

    @Test
    public void testMAXLengthNoneNext() {
        final ByteMessageBuffer buffer = new ByteMessageBuffer(400, charset);
        String mes = "<head>32769</head>dddddd";
        buffer.append(mes.getBytes());
        String m = buffer.next();
        Assert.assertNull(m);
        Assert.assertTrue(buffer.getPosition() == 0);
        Assert.assertTrue(buffer.getLimit() == 0);
    }

    @Test
    public void testMissMessage() {
        final ByteMessageBuffer buffer = new ByteMessageBuffer(400, charset);
        String mes = "12345678<head>";
        buffer.append(mes.getBytes());
        String m = buffer.next();
        Assert.assertNull(m);
        mes = "<head>00005</head>12345";
        buffer.append(mes.getBytes());
        m = buffer.next();
        Assert.assertEquals(mes, m);
        Assert.assertTrue(buffer.getPosition() == buffer.getLimit());
    }
}
