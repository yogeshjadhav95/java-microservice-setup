package com.prime.common.util;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.prime.common.library.SecurityLibrary;

@SuppressWarnings("serial")
@Component
public class CustomDecimalSerializer extends StdSerializer<BigDecimal> {

	public CustomDecimalSerializer() {
		this(null);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CustomDecimalSerializer(Class t) {
		super(t);
	}

	@Override
	public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
		gen.writeString(CalculationUtil.formatDecimal(value, SecurityLibrary.getDecimalScale()));
	}
}
