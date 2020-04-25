package com.github.wmarkow.klondiklon.tmx;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "object")
public class ObjectElement
{
    @Attribute(name = "id")
    int id;

    @Attribute(name = "gid")
    int gid;

    @Attribute(name = "x")
    double x;

    @Attribute(name = "y")
    double y;

    @Attribute(name = "width")
    int width;

    @Attribute(name = "height")
    int height;
}
