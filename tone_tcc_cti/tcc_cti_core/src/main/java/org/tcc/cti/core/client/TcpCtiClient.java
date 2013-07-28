package org.tcc.cti.core.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.tcc.cti.core.message.CtiMessageable;
import org.tcc.cti.core.model.ServerConfigure;

/**
 * 通过tcp协议实现客户端与cti服务器消息通信
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class TcpCtiClient implements CtiClientable{
	private final String companyId;
	private final String opId;
	private final ServerConfigure configure;
	
	private SocketChannel channel = null;
	
	public TcpCtiClient(String companyId,String opId,
			ServerConfigure configure){
		
		this.companyId = companyId;
		this.opId = opId;
		this.configure = configure;
	}

	@Override
	public String getCompanyId() {
		return companyId;
	}

	@Override
	public String getOPId() {
		return opId;
	}

	@Override
	public void start()throws ClientException {
		
		try {
			InetSocketAddress address = new InetSocketAddress(
					configure.getHost(), configure.getPort());
			channel = SocketChannel.open(address);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close()throws ClientException {
		// TODO Auto-generated method stub
		try {
			channel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void send(CtiMessageable message)throws ClientException {
		try {
			ByteBuffer buffer = ByteBuffer.wrap(message.getMessageISO());
			channel.write(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
