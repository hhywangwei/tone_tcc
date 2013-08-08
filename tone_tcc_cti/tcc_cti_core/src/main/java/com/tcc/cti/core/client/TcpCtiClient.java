package com.tcc.cti.core.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.buffer.ByteMessageBuffer;
import com.tcc.cti.core.client.buffer.MessageBuffer;
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
	
	private final String _companyId;
	private final String _opId;
	private final ServerConfigure _configure;
	
	private SocketChannel _channel = null;
	private List<SendHandler> _sendHandlers;
	private List<ReceiveHandler> _receiveHandlers;
	private StocketReceive _stocketReceive ;
		
	public TcpCtiClient(String companyId,String opId,
			ServerConfigure configure){
		
		_companyId = companyId;
		_opId = opId;
		_configure = configure;
	}

	@Override
	public synchronized void start()throws ClientException {
		
		try {
			InetSocketAddress address = new InetSocketAddress(
					_configure.getHost(), _configure.getPort());
			_channel = SocketChannel.open(address);
			_channel.configureBlocking(false);
			
			Selector selector = Selector.open();
			_channel.register(selector, SelectionKey.OP_READ);
			MessageBuffer messageBuffer = new ByteMessageBuffer();
			
			_stocketReceive = new StocketReceive(selector,messageBuffer);
			Thread t = new Thread(_stocketReceive);
			t.start();
		} catch (IOException e) {
			logger.error("Tcp client start is error \"{}\"",e);
			throw new ClientException(e);
		}			
	}

	@Override
	public void close()throws ClientException {
		try {
			if(_channel != null && _channel.isOpen()){
				_channel.close();				
			}
		} catch (IOException e) {
			logger.error("Tcp client close is error \"{}\"",e);
		}
	}

	@Override
	public void send(CtiMessage message)throws ClientException {
		for(SendHandler handler : _sendHandlers){
			handler.send(_channel, message);
		}
	}

	@Override
	public String getCompanyId() {
		return _companyId;
	}

	@Override
	public String getOPId() {
		return _opId;
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
		private static final Logger logger = LoggerFactory.getLogger(StocketReceive.class);
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
