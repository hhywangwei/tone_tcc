package com.tcc.cti.web.common.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * Json输出时格式日期为{@code yyyy-MM-dd HH:mm:ss}
 * 
 * @author hantongshan
 */
public class JsonTimestampSerializer extends JsonSerializer<Date> {
	private static final String PATTER = "yyyy-MM-dd HH:mm:ss";
	
	@Override
	public void serialize(Date date, JsonGenerator generator, SerializerProvider peovider)
			throws IOException, JsonProcessingException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(PATTER);
		
        String dateString = dateFormat.format(date);
        generator.writeString(dateString);
	}
}
