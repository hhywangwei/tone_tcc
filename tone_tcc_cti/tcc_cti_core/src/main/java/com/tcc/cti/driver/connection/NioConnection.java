package com.tcc.cti.driver.connection;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.driver.session.Session;
import com.tcc.cti.driver.session.Sessionable;

/**
 * 通过NIO实现服务器链接
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class NioConnection implements Connectionable {

    private static final Logger logger = LoggerFactory.getLogger(NioConnection.class);
    private static final int DEFAULT_TIMEOUT = 30 * 1000;

    private final Selector _selector;
    private final InetSocketAddress _address;
    private final int _timeout;

    public NioConnection(Selector selector, InetSocketAddress address) {
        this(selector, address, DEFAULT_TIMEOUT);
    }

    public NioConnection(Selector selector, InetSocketAddress address, int timeout) {
        _selector = selector;
        _address = address;
        _timeout = timeout;
    }

    @Override
    public SocketChannel connect(Sessionable session) throws IOException {
        return waitConnection(session, _selector, _address);
    }

    /**
     * 等待连接CTI服务器
     *
     * @param session {@link Session}
     * @param selector {@link Selector}
     * @param address CTI服务地址 {@link InetSocketAddress}
     * @return true 连接成功
     * @throws IOException
     * @throws InterruptedException
     */
    protected SocketChannel waitConnection(Sessionable session,
        Selector selector, InetSocketAddress address) throws IOException {

        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(address);
        channel.register(selector, SelectionKey.OP_CONNECT, session);
        boolean connection = false;

        int delay = 10;
        int count = (_timeout / delay);
        for (int i = 0; (!connection && (i < count)); i++) {
            logger.debug("{}.Wait connection......", i);

            if (selector.select(delay) == 0) {
                continue;
            }

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            try {
                for (; iterator.hasNext();) {
                    SelectionKey sk = iterator.next();
                    if (!sk.attachment().equals(session) || !sk.isConnectable()) {
                        continue;
                    }
                    if (channel.isConnectionPending() && channel.finishConnect()) {
                        logger.debug("{}.{} connection success", i, sk.attachment().toString());
                        channel.register(selector, SelectionKey.OP_READ, session);
                        connection = true;
                        break;
                    }
                }
            } catch (ConnectException e) {
                logger.error("{}.{} connection fail", i, session.toString());
                if (channel.isOpen()) {
                    channel.close();
                }
                throw e;
            }
        }

        if (!connection) {
            throw new ConnectException(
                "Connection timed out,timeout is " + _timeout);
        }

        return channel;
    }
}
