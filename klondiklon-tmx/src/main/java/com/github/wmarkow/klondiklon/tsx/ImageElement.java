package com.github.wmarkow.klondiklon.tsx;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "image")
public class ImageElement
{
    @Attribute(name = "source")
    private String source;

    @Attribute(name = "width")
    private int width;

    @Attribute(name = "height")
    private int height;

    public String getSource()
    {
        return source;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
