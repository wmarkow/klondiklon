package com.github.wmarkow.klondiklon.tmx;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "objectgroup")
public class ObjectGroupElement
{
    @Attribute(name = "id")
    private int id;

    @Attribute(name = "name")
    private String name;

    @ElementList(inline = true, name = "object")
    private List<ObjectElement> objects;

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public List<ObjectElement> getObjects()
    {
        return objects;
    }
}
