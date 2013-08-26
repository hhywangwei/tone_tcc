package com.tcc.cti.core.client;

import java.nio.channels.SocketChannel;
import java.util.List;

import com.tcc.cti.core.client.buffer.ByteMessageBuffer;
import com.tcc.cti.core.client.buffer.MessageBuffer;
import com.tcc.cti.core.client.receive.ReceiveHandler;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.client.sequence.MemoryGeneratorSeq;
import com.tcc.cti.core.message.CtiMessage;
import com.tcc.cti.core.message.pool.CtiMessagePool;

public class OperatorChannel {
	private final OperatorKey _operatorKey;
	private final GeneratorSeq _generator;
	private final SocketChannel _channel;
	private final MessageBuffer _messageBuffer;
	private final List<SendHandler> _sendHandlers;
	private final List<ReceiveHandler> _receiveHandlers;
	private final CtiMessagePool _messagePool;
	
	private volatile boolean _startReceive = false;

	public OperatorChannel(OperatorKey operatorKey,SocketChannel channel,
			List<SendHandler> sendHandlers,List<ReceiveHandler> receiveHandlers,
			CtiMessagePool messagePool) {

		this(operatorKey, channel, new MemoryGeneratorSeq(operatorKey._companyId, operatorKey._opId),
				sendHandlers,receiveHandlers,messagePool);
	}

	public OperatorChannel(OperatorKey operatorKey, SocketChannel channel,GeneratorSeq generator,
			List<SendHandler> sendHandlers,List<ReceiveHandler> receiveHandlers,
			CtiMessagePool messagePool){
		
		_operatorKey = operatorKey;
		_channel = channel;
		_generator = generator;
		_messageBuffer = new ByteMessageBuffer();
		_sendHandlers = sendHandlers;
		_receiveHandlers = receiveHandlers;
		_messagePool = messagePool;
	}

	public SocketChannel getChannel() {
		return _channel;
	}

	public GeneratorSeq getGenerator() {
		return _generator;
	}

	public OperatorKey getOperatorKey() {
		return _operatorKey;
	}
	
	public void append(byte[] bytes) throws InterruptedException {
		_messageBuffer.append(bytes);
	}
	
	public void startReceive(){
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				try{
					while(true){
						String m = _messageBuffer.next();
						for(ReceiveHandler handler: _receiveHandlers){
							handler.receive(m, _messagePool);
						}
					}	
				}catch(Exception e){
					//TODO 异常处理，特别注意阻塞问题
				}
			}
		});
		t.start();
		
		_startReceive = true;
	}
	
	public void send(CtiMessage message)throws ClientException {
		for(SendHandler handler : _sendHandlers){
			handler.send(_channel, message, _generator);
		}
	}
	
	public boolean isStartReceive(){
		return _startReceive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_operatorKey == null) ? 0 : _operatorKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperatorChannel other = (OperatorChannel) obj;
		if (_operatorKey == null) {
			if (other._operatorKey != null)
				return false;
		} else if (!_operatorKey.equals(other._operatorKey))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OperatorChannel [_operatorKey=");
		builder.append(_operatorKey.toString());
		builder.append("]");
		return builder.toString();
	}

	public static class OperatorKey {

		private final String _companyId;
		private final String _opId;

		public OperatorKey(String companyId, String opId) {
			_companyId = companyId;
			_opId = opId;
		}

		public String getCompanyId() {
			return _companyId;
		}

		public String getOpId() {
			return _opId;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("OperatorKey [_companyId=");
			builder.append(_companyId);
			builder.append(", _opId=");
			builder.append(_opId);
			builder.append("]");
			return builder.toString();
		}
	}
}
