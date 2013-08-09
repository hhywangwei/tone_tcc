package com.tcc.cti.core.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 接受消息返回代码定义，读取cti_receive_code.propertites属性文件得到返回代码定义
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public enum ReceiveCode {
	codeInstance(LoggerFactory.getLogger(ReceiveCode.class));
	
	private static final String FILE_NAME = "cti_receive_code.propertites";
	private static final String CODE_PREFIX = "return_code_";
	private static final String CHARSET = "UTF-8";
	
	private final Logger _logger;
	private final Map<String,String> _content;
	
	private ReceiveCode(Logger logger){
		_logger = logger;
		_content = new HashMap<String,String>();
		InputStream in = null;
		try{
			in = ReceiveCode.class.getResourceAsStream(FILE_NAME);
			InputStreamReader reader = new InputStreamReader(in,CHARSET);
			Properties p = new Properties();
			p.load(reader);
			for(Entry<Object,Object> e : p.entrySet()){
				String k = (String) e.getKey();
				String v = (String) e.getValue();
				_content.put(k, v);
			}
		}catch(Exception e){
			_logger.error("Load receive code is error {}",e);
			throw new RuntimeException("Load receive code is error",e);
		}finally{
			try{
				if(in != null) in.close();
			}catch(IOException e){
				//none instance
				_logger.error("close load receive error {}",e);
			}
		}
	}
	
	/**
	 * 得到返回消息描述
	 * 
	 * @param code 返回消息编号
	 * @return
	 */
	public String getDetail(String code){
		String c = CODE_PREFIX + code;
		return _content.get(c);
	}
}
