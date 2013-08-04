package com.tcc.cti.core.client.receive;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tcc.cti.core.client.ClientException;
import com.tcc.cti.core.message.pool.CtiMessagePool;

/**
 * 实现消息接受处理，实现了消息处理主要流程。
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public abstract class AbstractReceiveHandler implements ReceiveHandler{
	private static final Logger logger = LoggerFactory.getLogger(AbstractReceiveHandler.class);
	private static final String ROOT = "root";
	private static final String XML_FULL_PATTER = "<?xml version=\"1.0\"?>\n<root>%s</root>";
	private static final String MESSAGE_TYPE = "msg";
	
	@Override
	public void receive(String message,CtiMessagePool pool)throws ClientException{
		
		logger.debug("receive message is \"{}\"",message);
		
		try{
			Map<String,String> content = parseMessage(message);
			String msgType = content.get(MESSAGE_TYPE);
			if(StringUtils.isNotBlank(msgType) && isReceive(msgType)){
				receiveHandler(content, pool);
			}
		}catch(SAXException e){
			throw new ClientException(e);
		}
	}
	
	/**
	 * 解析消息得到{@link Map}消息集合，key是消息标准，value消息值。
	 * 
	 * @param message cit服务返回消息字符串
	 * @return 消息集合
	 * @throws SAXException
	 */
	protected Map<String,String> parseMessage(String message)throws SAXException {
		Map<String,String> content = new HashMap<String,String>();
		
		SAXParserFactory spf = SAXParserFactory.newInstance(); 
		try {
			SAXParser sp = spf.newSAXParser();
			String xml = String.format(XML_FULL_PATTER, message);
			
			logger.debug("message xml is \"{}\"",xml);
			
			StringReader reader = new StringReader(xml);
			sp.parse(new InputSource(reader), new XmlHandler(content));
		} catch (ParserConfigurationException e) {
			//none instance
			logger.error("ParserConfigurationException is exception:{}",e);
		} catch (IOException e) {
			//none instance
			logger.error("IOException is exception:{}",e);
		}
		
		return content;
	}
	
	/**
	 * 判读消息是否处理
	 * 
	 * @param msgType 消息类型
	 * @return
	 */
	protected abstract boolean isReceive(String msgType);
	
	/**
	 * 处理接受消息
	 * 
	 * @param content 接受消息内容
	 * @param pool 消息接收池
	 * @throws ClientException
	 */
	protected abstract void receiveHandler(Map<String,String> content,CtiMessagePool pool)throws ClientException;
	
	/**
	 * 接受消息解析处理类，使用SAX解析器处理
	 * 
	 * @author <a href="hhywangwei@gmail.com">wangwei</a>
	 */
	protected static class XmlHandler extends DefaultHandler{
		
		private final Map<String,String> _content;
		private StringBuilder _value =new StringBuilder();
		
		public XmlHandler(Map<String,String> content){
			_content = content;
		}
		
		@Override
		public void startElement(String uri,String localName,String qName,Attributes attributes)throws SAXException{
			int len = _value.length();
			if(len != 0){
				_value.delete(0, len);
			}
		}
		
		@Override
		public void characters(char[] ch, int start, int length)throws SAXException{
			_value.append(new String(ch,start,length));
		}
		
		@Override
		public void endElement(String uri,String localName,String qName)throws SAXException{
			if(ROOT.equals(qName)) return ;
			
			String v = _value.toString();
			logger.debug("element is \"{}\",_value is \"{}\"",qName,v);
			_content.put(qName, v);
		}
	}
	
}
