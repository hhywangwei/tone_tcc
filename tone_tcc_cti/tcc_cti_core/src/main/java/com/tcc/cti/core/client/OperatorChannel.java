package com.tcc.cti.core.client;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;

import com.tcc.cti.core.client.buffer.ByteMessageBuffer;
import com.tcc.cti.core.client.buffer.MessageBuffer;
import com.tcc.cti.core.client.send.SendHandler;
import com.tcc.cti.core.client.sequence.GeneratorSeq;
import com.tcc.cti.core.client.sequence.MemoryGeneratorSeq;
import com.tcc.cti.core.message.send.SendMessage;

/**
 * 每个操作员与通过一个socket与cti服务器连接
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class OperatorChannel {
	private final OperatorKey _operatorKey;
	private final GeneratorSeq _generator;
	private final SocketChannel _channel;
	private final MessageBuffer _messageBuffer;
	private final List<SendHandler> _sendHandlers;
	private final String _charset;

	public OperatorChannel(OperatorKey operatorKey,SocketChannel channel,
			List<SendHandler> sendHandlers,String charset) {

		this(operatorKey, channel, sendHandlers, charset,
				new MemoryGeneratorSeq(operatorKey._companyId, operatorKey._opId));
	}

	public OperatorChannel(OperatorKey operatorKey, SocketChannel channel,
			List<SendHandler> sendHandlers,String charset,GeneratorSeq generator){
		
		_operatorKey = operatorKey;
		_channel = channel;
		_generator = generator;
		_messageBuffer = new ByteMessageBuffer(charset);
		_sendHandlers = sendHandlers;
		_charset = charset;
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
	
	public String next(){
		return _messageBuffer.next();
	}
	
	public boolean isOpen(){
		return _channel.isOpen();
	}
	
	public void send(SendMessage message)throws ClientException {
		for(SendHandler handler : _sendHandlers){
			handler.send(_channel, message, _generator,_charset);
		}
	}
	
	public void close()throws ClientException{
		try{
			if(_channel.isOpen()){
				_channel.close();
			}
		}catch(IOException e){
			throw new ClientException(e);
		}
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
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((_companyId == null) ? 0 : _companyId.hashCode());
			result = prime * result + ((_opId == null) ? 0 : _opId.hashCode());
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
			OperatorKey other = (OperatorKey) obj;
			if (_companyId == null) {
				if (other._companyId != null)
					return false;
			} else if (!_companyId.equals(other._companyId))
				return false;
			if (_opId == null) {
				if (other._opId != null)
					return false;
			} else if (!_opId.equals(other._opId))
				return false;
			return true;
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
