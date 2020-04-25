package com.github.wmarkow.klondiklon.tsx;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "tile")
public class TileElement
{
    @Attribute(name = "id")
    private int id;

    @ElementList(name = "properties", entry = "property")
    private List<PropertyElement> properties;

    public int getId()
    {
        return id;
    }

    public List<PropertyElement> getProperties()
    {
        return properties;
    }
}
