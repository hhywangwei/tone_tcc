package com.tcc.cti.core.client.connection;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.tcc.cti.core.client.session.Session;
import com.tcc.cti.core.client.session.Sessionable;

/**
 * 链接服务器接口 
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public interface Connectionable {
	
	/**
	 * 链接服务器
	 * 
	 * @param oc {@link Session}
	 * @return 链接成功{@link SocketChannel}
	 * @throws IOException
	 */
	SocketChannel connect(Sessionable session)throws IOException;
}
