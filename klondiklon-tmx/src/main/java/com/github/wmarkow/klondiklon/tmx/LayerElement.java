package com.github.wmarkow.klondiklon.tmx;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "layer")
public class LayerElement
{
    @Attribute(name = "id")
    private int id;

    @Attribute(name = "name")
    private String name;

    @Attribute(name = "width")
    private int width;

    @Attribute(name = "height")
    private int height;

    @Element(name = "data")
    private LayerDataElement data;

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public LayerDataElement getData()
    {
        return data;
    }

}
