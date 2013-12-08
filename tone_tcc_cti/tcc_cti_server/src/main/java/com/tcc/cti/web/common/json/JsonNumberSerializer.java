package com.tcc.cti.web.common.json;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class JsonNumberSerializer extends JsonSerializer<Number> {
    private static final String PATTER = "0.00";
    
    @Override
    public void serialize(Number number, JsonGenerator generator,
            SerializerProvider peovider)throws IOException,JsonProcessingException {
        
        NumberFormat dateFormat = new DecimalFormat(PATTER);
        
        String v = dateFormat.format(number);
        generator.writeString(v);
        
    }

}
