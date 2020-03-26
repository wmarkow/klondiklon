package com.github.wmarkow.klondiklon.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

public class KKObjectMapper extends ObjectMapper
{
    private static final long serialVersionUID = 7862801075351651020L;

    public KKObjectMapper() {
        this(null, null, null);
    }

    public KKObjectMapper(JsonFactory jf, DefaultSerializerProvider sp, DefaultDeserializationContext dc) {
        super(jf, sp, dc);

        _deserializationContext = (dc == null) ? new DefaultDeserializationContext.Impl(new KKBeanDeserializerFactory())
                : dc;

        disable(MapperFeature.AUTO_DETECT_CREATORS, MapperFeature.AUTO_DETECT_FIELDS, MapperFeature.AUTO_DETECT_GETTERS,
                MapperFeature.AUTO_DETECT_IS_GETTERS);
        enable(SerializationFeature.INDENT_OUTPUT);
    }
}
