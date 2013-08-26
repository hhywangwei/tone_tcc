package com.tcc.cti.core.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.buffer.ByteMessageBuffer;
import com.tcc.cti.core.client.buffer.MessageBuffer;
import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.message.CtiMessage;
import com.tcc.cti.core.message.pool.CtiMessagePool;
import com.tcc.cti.core.model.ServerConfigure;

/**
 * 通过tcp协议实现客户端与cti服务器消息通信
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class TcpCtiClient implements CtiClientable{
	private static final Logger logger = LoggerFactory.getLogger(TcpCtiClient.class);
	
	private final ServerConfigure _configure;
	private final CtiMessagePool _messagePool;
	private final Map<OperatorChannel.OperatorKey, OperatorChannel> _channelPool = 
			Collections.synchronizedMap(new HashMap<OperatorChannel.OperatorKey, OperatorChannel>());
	
	private List<SendHandler> _sendHandlers;
	private List<ReceiveHandler> _receiveHandlers;
	private StocketReceive _stocketReceive ;
	private Selector _selector;
		
	public TcpCtiClient(ServerConfigure configure,CtiMessagePool messagePool){
		_configure = configure;
		_messagePool = messagePool;
	}

	@Override
	public synchronized void start()throws ClientException {
		
		try {
			
			_selector = Selector.open();
			MessageBuffer messageBuffer = new ByteMessageBuffer();
			_stocketReceive = new StocketReceive(_selector, messageBuffer);
			Thread t = new Thread(_stocketReceive);
			t.start();
			
		} catch (IOException e) {
			logger.error("Tcp client start is error {}",e.toString());
			throw new ClientException(e);
		}			
	}
	

	public void register(String companyId,String opId)throws ClientException{
		try {
			InetSocketAddress address = new InetSocketAddress(
					_configure.getHost(), _configure.getPort());
			SocketChannel channel = SocketChannel.open(address);
			channel.configureBlocking(false);	
			OperatorChannel.OperatorKey key = 
					new OperatorChannel.OperatorKey(companyId, opId);
			channel.register(_selector, SelectionKey.OP_READ,key);
			OperatorChannel operatorChannel = new OperatorChannel(
					key,channel,_sendHandlers,_receiveHandlers,_messagePool);
			synchronized (_channelPool) {
				_channelPool.put(key, operatorChannel);
			}
		}catch(IOException e){
			logger.error("Tcp client start is error {}",e.toString());
			throw new ClientException(e);
		}
	}

	@Override
	public void close()throws ClientException {
//		try {
//			if(_channel != null && _channel.isOpen()){
//				_channel.close();				
//			}
//		} catch (IOException e) {
//			logger.error("Tcp client close is error {}",e);
//		}
	}

	@Override
	public void send(CtiMessage message)throws ClientException {
		OperatorChannel channel = null;
		OperatorChannel.OperatorKey key = 
				new OperatorChannel.OperatorKey(message.getCompayId(), message.getOpId());
		synchronized (_channelPool) {
			channel = _channelPool.get(key);
		}
		channel.send(message);
	}

	@Override
	public void setReceiveHandlers(List<ReceiveHandler> handlers) {
		this._receiveHandlers = handlers;
	}

	@Override
	public void setSendHandlers(List<SendHandler> handlers) {
		this._sendHandlers = handlers;
	}
	
	static class StocketReceive implements Runnable {
		private static final int DEFAULT_BUFFER_SIZE = 1024 * 8;
		
		private final Selector _selector;
		private final MessageBuffer _messageBuffer;
		private final int _bufferSize ;
		
		public StocketReceive(Selector selector,MessageBuffer messageBuffer){
			this(selector,messageBuffer,DEFAULT_BUFFER_SIZE);
		}
		
		public StocketReceive(Selector selector,MessageBuffer messageBuffer,int bufferSize){
			_selector = selector;
			_messageBuffer= messageBuffer;
			_bufferSize = bufferSize;
		}

		public void run() {
			try{
				ByteBuffer buffer = ByteBuffer.allocateDirect(_bufferSize);
				while(_selector.select() > 0){
					for(SelectionKey sk : _selector.selectedKeys()){
						if(!sk.isReadable()) continue;
						SocketChannel sc =(SocketChannel) sk.channel();
						sc.read(buffer);
						buffer.flip();
						byte[] bytes = new byte[buffer.remaining()];
						buffer.get(bytes);
						_messageBuffer.append(bytes);
						buffer.clear();
					}
				}
			}catch(Exception e){
				//TODO 需要处理阻塞异常
				//TODO 可能需要更多异常处理
				logger.error("Recevice message is error {}",e);
			}
		}
	}
}
