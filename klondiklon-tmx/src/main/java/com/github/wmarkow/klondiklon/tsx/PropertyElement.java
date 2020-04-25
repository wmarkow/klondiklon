package com.github.wmarkow.klondiklon.tsx;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "property")
public class PropertyElement
{
    @Attribute(name = "name")
    private String name;

    @Attribute(name = "value")
    private String value;

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }
}
