package com.github.wmarkow.klondiklon.tiled.tsx;

import java.util.Collections;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "tile")
public class TileElement
{
    @Attribute(name = "id")
    private int id;

    @ElementList(name = "properties", entry = "property", required = false)
    private List<PropertyElement> properties;
    
    @ElementList(name = "animation", entry = "frame", required = false)
    private List<FrameElement> animationFrames;

    public int getId()
    {
        return id;
    }

    public List<PropertyElement> getProperties()
    {
        if(properties == null)
        {
            return Collections.EMPTY_LIST;
        }
        
        return properties;
    }

    public List<FrameElement> getAnimationFrames()
    {
        if(animationFrames == null)
        {
            return Collections.EMPTY_LIST;
        }
        
        return animationFrames;
    }
}
