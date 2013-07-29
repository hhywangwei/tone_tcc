package com.tcc.cti.core.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.message.CtiMessageable;
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
			 
			Thread t = new Thread(new ReadRun(selector));
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
	public void send(CtiMessageable message)throws ClientException {
		try {
			ByteBuffer buffer = ByteBuffer.wrap(message.getMessageISO());
			channel.write(buffer);
		} catch (IOException e) {
			logger.error("Tcp client send message is error \"{}\"",e);
		}
	}
	
	public static class ReadRun implements Runnable{
		private final Selector selector;
		
		public ReadRun(Selector selector){
			this.selector = selector;
		}

		public void run() {
			try{
				while(selector.select() > 0){
					for(SelectionKey sk : selector.selectedKeys()){
						if(!sk.isReadable()) continue;
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						SocketChannel sc =(SocketChannel) sk.channel();
						sc.read(buffer);
						buffer.flip();
						
						String r = Charset.forName("iso-8859-1").newDecoder().decode(buffer).toString();
						
						System.out.println(r);
					}
				}
			}catch(Exception e){
				
			}
			
		}
		
	}
}
