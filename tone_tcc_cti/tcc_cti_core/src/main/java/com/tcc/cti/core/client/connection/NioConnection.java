package com.tcc.cti.core.client.connection;

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

import com.tcc.cti.core.client.OperatorChannel;

public class NioConnection implements Connectionable{
	private static final Logger logger = LoggerFactory.getLogger(NioConnection.class);
	private static final int DEFAULT_TIMEOUT = 30 * 1000;
	
	private final Selector _selector;
	private final InetSocketAddress _address;
	private final int _timeout;

	public NioConnection(Selector selector,InetSocketAddress address){
		this(selector,address,DEFAULT_TIMEOUT);
	}
	
	public NioConnection(Selector selector,InetSocketAddress address,int timeout){
		_selector = selector;
		_address = address;
		_timeout = timeout;
	}

	@Override
	public boolean connect(OperatorChannel oc) throws IOException {
		return waitConnection(oc,_selector,_address);
	}
	
	/**
	 * 等待连接CTI服务器
	 * 
	 * @param oc {@link OperatorChannel}
	 * @param selector {@link Selector}
	 * @param address CTI服务地址  {@link InetSocketAddress}
	 * @return true 连接成功
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	protected boolean waitConnection(OperatorChannel oc,
			Selector selector,InetSocketAddress address)throws IOException{
		
		SocketChannel channel = oc.getChannel();
		channel.configureBlocking(false);
		channel.connect(address);
		channel.register(selector, SelectionKey.OP_CONNECT, oc);
		boolean connection = false;
		
		int delay = 10;
		int count= (_timeout / delay);
		for(int i= 0; (!connection && (i< count)); i++){
			logger.debug("{}.Wait connection......",i);
			
			if(selector.select(delay) == 0){
				continue;
			}
			
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			try{
				for(;iterator.hasNext();){
					SelectionKey sk = iterator.next();
					if(!sk.attachment().equals(oc) || !sk.isConnectable()){
						continue;
					}
					if(channel.isConnectionPending() && channel.finishConnect()){
						logger.debug("{}.{} connection success", i, sk.attachment().toString());
						channel.register(selector, SelectionKey.OP_READ, oc);
						connection = true;
						break;
					}	
				}				
			}catch(ConnectException e){
				logger.error("{}.{} connection fail", i, oc.toString());
				if(channel.isOpen()){
					channel.close();
				}
				throw e;
			}
		}
		
		return connection;
	}
}
