package com.github.wmarkow.klondiklon.tmx;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "objectgroup")
public class ObjectGroupElement
{
    @Attribute(name = "id")
    int id;

    @Attribute(name = "name")
    String name;

    @ElementList(inline = true, name = "object")
    List<ObjectElement> objects;
}
