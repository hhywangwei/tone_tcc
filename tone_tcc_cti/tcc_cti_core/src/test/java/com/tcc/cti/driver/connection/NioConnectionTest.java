package com.tcc.cti.driver.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.driver.Configure;
import com.tcc.cti.driver.session.Sessionable;

/**
 * {@link NioConnection}单元测试
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class NioConnectionTest {

    private static final Logger logger = LoggerFactory.getLogger(NioConnectionTest.class);
    private Configure _configure;

    @Before
    public void before() throws Exception {
        _configure = new Configure.Builder("211.136.173.132", 9999).
            build();
    }

    @Test
    public void testWaitConnection() throws Exception {
        InetSocketAddress address = new InetSocketAddress(
            _configure.getHost(), _configure.getPort());
        Selector selector = Selector.open();
        Connectionable conn = new NioConnection(selector, address);

        Sessionable oc = Mockito.mock(Sessionable.class);
        SocketChannel channel = conn.connect(oc);
        Assert.assertNotNull(channel);
        Assert.assertTrue(channel.isOpen());
        channel.close();
    }

    @Test
    public void testWaitConnectionAddressErrorOrTimeOut() throws Exception {
        Configure configure = new Configure.Builder("211.136.173.134", 9999).
            build();

        InetSocketAddress address = new InetSocketAddress(
            configure.getHost(), configure.getPort());
        Selector selector = Selector.open();
        Connectionable conn = new NioConnection(selector, address);

        Sessionable oc = Mockito.mock(Sessionable.class);
        try {
            conn.connect(oc);
            Assert.fail("Connection not exception");
        } catch (IOException e) {
            logger.debug("Connection error is {}", e.toString());
        }
    }

    @Test
    public void testWaitConnectionTimeOut() throws Exception {
        Configure configure = new Configure.Builder("211.136.173.134", 9999).
            build();

        InetSocketAddress address = new InetSocketAddress(
            configure.getHost(), configure.getPort());
        Selector selector = Selector.open();
        Connectionable conn = new NioConnection(selector, address, 10);

        Sessionable oc = Mockito.mock(Sessionable.class);
        try {
            conn.connect(oc);
            Assert.fail("Connection not exception");
        } catch (IOException e) {
            logger.debug("Connection timeout:{}", e.getMessage());
            Assert.assertEquals("Connection timed out,timeout is 10", e.getMessage());
        }
    }
}
