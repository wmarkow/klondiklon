package com.github.wmarkow.klondiklon.tsx;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "frame")
public class FrameElement
{
    @Attribute(name = "tileid")
    private int tileid;

    @Attribute(name = "duration")
    private int duration;

    public int getTileid()
    {
        return tileid;
    }

    public int getDuration()
    {
        return duration;
    }
}
