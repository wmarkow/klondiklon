package com.github.wmarkow.klondiklon.tmx;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "tileset")
public class TilesetElement
{
    @Attribute(name = "firstgid")
    private int firstgid;

    @Attribute(name = "source")
    private String source;

    public int getFirstgid()
    {
        return firstgid;
    }

    public String getSource()
    {
        return source;
    }
}
