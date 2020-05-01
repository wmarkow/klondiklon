package com.github.wmarkow.klondiklon.tiled.tmx;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "object")
public class ObjectElement
{
    @Attribute(name = "id")
    private int id;

    @Attribute(name = "gid")
    private int gid;

    @Attribute(name = "x")
    private double x;

    @Attribute(name = "y")
    private double y;

    @Attribute(name = "width")
    private double width;

    @Attribute(name = "height")
    private double height;

    public int getId()
    {
        return id;
    }

    public int getGid()
    {
        return gid;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }
    
    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getWidth()
    {
        return width;
    }

    public double getHeight()
    {
        return height;
    }
}
