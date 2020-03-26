package com.github.wmarkow.klondiklon.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;

public class KKBeanDeserializerFactory extends BeanDeserializerFactory
{
    private static final long serialVersionUID = -5418900912456305680L;

    public KKBeanDeserializerFactory() {
        super(new DeserializerFactoryConfig());
    }

    protected BeanDeserializerBuilder constructBeanDeserializerBuilder(DeserializationContext ctxt,
            BeanDescription beanDesc)
    {
        return new KKBeanDeserializerBuilder(beanDesc, ctxt);
    }
}
