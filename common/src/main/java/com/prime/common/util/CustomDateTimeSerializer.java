package com.prime.common.util;

import java.io.IOException;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.prime.common.library.SecurityLibrary;

@Component
public class CustomDateTimeSerializer extends StdSerializer<Date> {

	private static final long serialVersionUID = -6206316078737191005L;

	public CustomDateTimeSerializer() {
		this(null);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CustomDateTimeSerializer(Class t) {
		super(t);
	}

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
		try {
			gen.writeString(DateUtils.formatDate(value, SecurityLibrary.getTimeZone()));
		} catch (Exception e) {
			gen.writeString(DateUtils.formatDate(value));
		}
	}
}
