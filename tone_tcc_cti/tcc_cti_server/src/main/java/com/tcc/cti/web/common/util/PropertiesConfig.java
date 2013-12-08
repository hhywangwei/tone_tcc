package com.tcc.cti.web.common.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

public class PropertiesConfig {
	
	private final Log log = LogFactory.getLog(PropertiesConfig.class);
	
	private static String PROPERTIES_CONFIG_FILENAME = "/paraconfig.properties";
	
	private Properties properties = new Properties();
	
	private static PropertiesConfig instance = null;

	private PropertiesConfig(){
		init();
	}
	
	public static PropertiesConfig getInstance(){
		if(instance == null){
			instance = new PropertiesConfig();
		}
		return instance;
	}
	
	public void init(){
		log.info("PropertiesConfig begin init.........");
		InputStream inStream = null;
		try {
			inStream = this.getClass().getResourceAsStream(PROPERTIES_CONFIG_FILENAME);
			if(inStream != null)
				properties.load(inStream);
			log.info("PropertiesConfig init success");
		} catch (Exception e) {
			log.error("PropertiesConfig begin init error", e);
		}
	}
	
	public String getProperty(String key){
		if(properties.isEmpty()){
			init();
		}
		String value = properties.getProperty(key);
		log.info("PropertiesConfig getProperty key:" + key + ",value:" + value);
		return value;
	}
	
	public void setProperty(String key,String value){
		if(properties.isEmpty()){
			init();
		}
		properties.setProperty(key, value);
		log.info("PropertiesConfig setProperty key:" + key + ",value:" + value);
	}
	
	public Integer getPageSize(){
		log.info("PropertiesConfig begin getPageSize............");
		String pasize = "";
		Integer pagesize = null;
		try {
			pasize = getProperty("pagesize");
			if(StringUtils.isNotEmpty(pasize)){
				pagesize = Integer.parseInt(pasize);
				log.info("PropertiesConfig getPageSize pagesize:"+pagesize);
			}else{
				pagesize = 10;
				log.debug("pagesize property is null,use dafault value:"+10);
			}
		} catch (Exception e) {
			log.error("PropertiesConfig getPageSize error:", e);
		}
		return pagesize;
	}
	
	public static void main(String[] args) {
		PropertiesConfig config = PropertiesConfig.getInstance();
		//System.out.println("***********pagesize:"+config.getProperty("pagesize"));
		config.setProperty("starname", "hantongshan");
		System.out.println("***********starname:"+config.getProperty("starname"));
		System.out.println("***********pagesize:"+config.getProperty("pagesize"));
	}
	
}
