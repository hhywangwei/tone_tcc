package com.tcc.cti.core.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.message.CtiMessage;
import com.tcc.cti.core.model.ServerConfigure;

/**
 * 通过tcp协议实现客户端与cti服务器消息通信
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class TcpCtiClient implements CtiClientable{
	private static final Logger logger = LoggerFactory.getLogger(TcpCtiClient.class);
	
	private final String companyId;
	private final String opId;
	private final ServerConfigure configure;
	
	private SocketChannel channel = null;
	
	private List<SendHandler> sendHandlers;
	
	private List<ReceiveHandler> receiveHandlers;
		
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
			channel.configureBlocking(false);
			Selector selector = Selector.open();
			channel.register(selector, SelectionKey.OP_READ);
			//TODO 设置消息池
			CtiReceiveRunner runner = new CtiReceiveRunner(selector,receiveHandlers,null);
			Thread t = new Thread(runner);
			t.start();
		} catch (IOException e) {
			logger.error("Tcp client start is error \"{}\"",e);
			throw new ClientException(e);
		}			
	}

	@Override
	public void close()throws ClientException {
		try {
			if(channel != null && channel.isOpen()){
				channel.close();				
			}
		} catch (IOException e) {
			logger.error("Tcp client close is error \"{}\"",e);
		}
	}

	@Override
	public void send(CtiMessage message)throws ClientException {
		for(SendHandler handler : sendHandlers){
			handler.send(channel, message);
		}
	}

	@Override
	public void setReceiveHandlers(List<ReceiveHandler> handlers) {
		this.receiveHandlers = handlers;
	}

	@Override
	public void setSendHandlers(List<SendHandler> handlers) {
		this.sendHandlers = handlers;
	}
}
