package com.tcc.cti.core.client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.message.pool.CtiMessagePool;
import com.tcc.cti.core.message.pool.OperatorCtiMessagePool;
import com.tcc.cti.core.message.request.RequestMessage;
import com.tcc.cti.core.model.ServerConfigure;

/**
 * 通过tcp协议实现客户端与cti服务器消息通信
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class TcpCtiClient implements CtiClientable{
	private static final Logger logger = LoggerFactory.getLogger(TcpCtiClient.class);
	private static final String DEFAULT_CHARSET_NAME = "ISO-8859-1";
	private static final int DEFAULT_TIMEOUT = 30 * 1000;

	private final CtiMessagePool _messagePool;
	private final InetSocketAddress _address;
	private final ConcurrentHashMap<OperatorKey, OperatorChannel> _channelPool = 
			new ConcurrentHashMap<OperatorKey, OperatorChannel>();
	
	private List<SendHandler> _sendHandlers;
	private List<ReceiveHandler> _receiveHandlers;
	private StocketReaderRunner _readerRunner;
	private Selector _selector;
	private int _timeOut = DEFAULT_TIMEOUT;
	private String _charset = DEFAULT_CHARSET_NAME;
	
	public TcpCtiClient(ServerConfigure configure){
		this(configure,new OperatorCtiMessagePool());
	}
		
	public TcpCtiClient(ServerConfigure configure,CtiMessagePool messagePool){
		_messagePool = messagePool;
		_address = new InetSocketAddress(
				configure.getHost(), configure.getPort());
	}

	@Override
	public void start()throws ClientException {
		try {
			_selector = Selector.open();

			_readerRunner= new StocketReaderRunner(_selector,_channelPool);
			Thread t = new Thread(_readerRunner);
			t.start();

		} catch (IOException e) {
			logger.error("Tcp client start is error {}",e.toString());
			throw new ClientException(e);
		}	
	}
	

	public boolean register(String companyId,String opId)throws ClientException{
			OperatorKey key =new OperatorKey(companyId, opId);
			if(isRegister(key)){
				return true;
			}
			
			return connectionServer(key); 
	}
	
	private boolean isRegister(OperatorKey key){
		return _channelPool.contains(key) &&_channelPool.get(key).isOpen();
	}
	
	private boolean connectionServer(OperatorKey key)throws ClientException{
		boolean success = false;
		try {
			_readerRunner.suspend();
			SocketChannel channel = SocketChannel.open();
			String host = _address.getHostName();
			int port = _address.getPort();
			if(waitConnection(key,channel,_selector,_address)){
				OperatorChannel oc = new OperatorChannel.
						Builder(key,channel,_messagePool).
						setReceiveHandlers(_receiveHandlers).
						setSendHandlers(_sendHandlers).
						setCharset(_charset).
						build();
				oc.start();
				if(_channelPool.putIfAbsent(key, oc) != null){
					logger.warn("Connection {}:{} is exist",host,port);
					channel.close();
				}
				success = true;
			}else{
				logger.error("Connection {}:{} is timeout",host,port);
				throw new ClientException("Connection "+ host + ":" + port +" is timeout");
			}
		}catch(IOException e){
			logger.error("Tcp client start is error {}",e.toString());
			throw new ClientException(e);
		}finally{
			_readerRunner.restart();
		}
		return success;
	}
	
	/**
	 * 等待连接CTI服务器
	 * 
	 * @param key 操作键  {@link OperatorKey}
	 * @param channel {@link SocketChannel}
	 * @param selector {@link Selector}
	 * @param address CTI服务地址  {@link InetSocketAddress}
	 * @return true 连接成功
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	protected boolean waitConnection(OperatorKey key,
			SocketChannel channel, Selector selector,
			InetSocketAddress address)throws IOException{
		
		channel.configureBlocking(false);
		channel.connect(address);
		channel.register(selector,  SelectionKey.OP_CONNECT,key);
		
		int delay= 10;
		int count= (_timeOut / delay);
		for(int i= 0; i< count; i++){
			logger.debug("{}.wait connection......",i);
			
			if(selector.select(delay) == 0){
				continue;
			}
			
			while(true){
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectedKeys.iterator();
				while(iterator.hasNext()){
					SelectionKey sk = iterator.next();
					if(sk.attachment().equals(key) && sk.isConnectable()){
						try{
							if(channel.isConnectionPending() && channel.finishConnect()){
								logger.debug("{}.{} connection success", i, sk.attachment());
								channel.register(selector, SelectionKey.OP_READ, key);
								return true;
							}	
						}catch(ConnectException e){
							logger.error("{}.{} connection fail", i, sk.attachment());
							throw e;
						}
						iterator.remove();
					}
				}
			}
		}
		
		if(channel.isOpen()){
			channel.close();
		}
		return false;
	}
	
	public void unRegister(String companyId,String opId)throws ClientException{
		OperatorKey key = 
				new OperatorKey(companyId, opId);
		OperatorChannel  oc = _channelPool.remove(key);
		if(oc != null && oc.isOpen()){
			oc.close();			
		}
	}

	@Override
	public void close()throws ClientException {
		for(OperatorKey key : _channelPool.keySet()){
			OperatorChannel channel = _channelPool.remove(key);
			if(channel != null && channel.isOpen()){
				channel.close();
			}
		}	
	}

	@Override
	public void send(RequestMessage message)throws ClientException {
		OperatorKey key = 
				new OperatorKey(
						message.getCompayId(), message.getOpId());
		OperatorChannel channel = _channelPool.get(key);
		if(channel.isOpen()){
			channel.send(message);
		}
	}

	@Override
	public void setReceiveHandlers(List<ReceiveHandler> handlers) {
		this._receiveHandlers = handlers;
	}

	@Override
	public void setSendHandlers(List<SendHandler> handlers) {
		this._sendHandlers = handlers;
	}
	
	@Override
	public void setTimeOut(int timeOut){
		_timeOut = timeOut * 1000 ;
	}
	
	@Override
	public void setCharset(String charset){
		_charset = charset;
	}
	
	@Override
	public CtiMessagePool getMessagePool(){
		return _messagePool;
	}
	
	static class StocketReaderRunner implements Runnable {
		private static final int DEFAULT_BUFFER_SIZE = 1024 * 8;
		
		private final Selector _selector;
		private final int _bufferSize ;
		private final Object _suspendLock = new Object();
		private final Map<OperatorKey, OperatorChannel> _channelPool;
		
		private volatile boolean _suspend = false;
		
		public StocketReaderRunner(Selector selector,
				Map<OperatorKey, OperatorChannel> channelPool){
			
			this(selector,channelPool,DEFAULT_BUFFER_SIZE);
		}
		
		public StocketReaderRunner(Selector selector,
				Map<OperatorKey, OperatorChannel> channelPool,
				int bufferSize){
			
			_selector = selector;
			_bufferSize = bufferSize;
			_channelPool = channelPool;
		}

		public void run() {
			try{
				ByteBuffer buffer = ByteBuffer.allocateDirect(_bufferSize);
				while(true){
					if(_suspend){
						logger.debug("ReaderRunner is suspend");
						synchronized (_suspendLock) {
							_suspendLock.wait();
						}
					}
					
					if(_selector.select() == 0){
						continue;
					}
					
					Set<SelectionKey> selectedKeys = _selector.selectedKeys();
					Iterator<SelectionKey> iterator = selectedKeys.iterator();					
					while(iterator.hasNext()){
						SelectionKey sk = iterator.next();
						if(!sk.isReadable()) continue;
						SocketChannel sc =(SocketChannel) sk.channel();
						sc.read(buffer);
						buffer.flip();
						byte[] bytes = new byte[buffer.remaining()];
						buffer.get(bytes);
						OperatorKey key = 
									(OperatorKey)sk.attachment();
						OperatorChannel channel = _channelPool.get(key);
						channel.append(bytes);
						buffer.clear();
						iterator.remove();
					}
				}
			}catch(InterruptedException e){
				logger.error("ReaderRunner spspend is error:{}",e.toString());
				Thread.currentThread().interrupt();
			}catch(Exception e){
				//TODO 需要处理阻塞异常
				//TODO 可能需要更多异常处理
				logger.error("Recevice message is error {}",e);
			}
		}
		
		/**
		 * 从新开始{@link Selector.select()},接收注册服务端数据
		 */
		public void restart(){
			_suspend = false;
			synchronized (_suspendLock) {
				logger.debug("ReaderRunner is restart");
				_suspendLock.notifyAll();
			}
		}
		
		/**
		 * 暂停{@link Selector.select()}并从中断恢复，使{@link Selector}可以接收其他操作
		 * 
		 * @throws InterruptedException
		 */
		public void suspend() {
			_suspend = true;
			_selector.wakeup();
		}
	}
}
