package com.tcc.cti.driver.connection;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.tcc.cti.driver.session.Session;
import com.tcc.cti.driver.session.Sessionable;

/**
 * 链接服务器接口
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface Connectionable {

    /**
     * 链接服务器
     *
     * @param session {@link Session}
     * @return 链接成功{@link SocketChannel}
     * @throws IOException
     */
    SocketChannel connect(Sessionable session) throws IOException;
}
