package com.github.wmarkow.klondiklon.tiled.tmx;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "data")
public class LayerDataElement
{
    @Attribute(name = "encoding")
    private String encoding;

    @Text
    private String data;

    public String getEncoding()
    {
        return encoding;
    }

    public String getData()
    {
        return data;
    }
}
