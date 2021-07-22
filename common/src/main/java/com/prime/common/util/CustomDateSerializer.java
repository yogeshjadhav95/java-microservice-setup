package com.prime.common.util;

import java.io.IOException;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@Component
public class CustomDateSerializer extends StdSerializer<Date> {

	private static final long serialVersionUID = 7758061656926917364L;

	public CustomDateSerializer() {
		this(null);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CustomDateSerializer(Class t) {
		super(t);
	}

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		gen.writeString(DateUtils.formatDate(value));
	}
}
