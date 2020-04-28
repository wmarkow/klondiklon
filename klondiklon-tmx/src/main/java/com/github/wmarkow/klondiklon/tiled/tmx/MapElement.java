package com.github.wmarkow.klondiklon.tiled.tmx;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "map")
public class MapElement
{
    @Attribute(name = "version")
    private String version;

    @Attribute(name = "tiledversion")
    private String tiledVersion;

    @Attribute(name = "orientation")
    private String orientation = "isometric";

    @Attribute(name = "renderorder")
    private String renderorder;

    @Attribute(name = "compressionlevel")
    private int compressionlevel;

    @Attribute(name = "width")
    private int width;

    @Attribute(name = "height")
    private int height;

    @Attribute(name = "tilewidth")
    private int tilewidth;

    @Attribute(name = "tileheight")
    private int tileheight;

    @Attribute(name = "infinite")
    private int infinite;

    @Attribute(name = "nextlayerid")
    private int nextlayerid;

    @Attribute(name = "nextobjectid")
    private int nextobjectid;

    @ElementList(inline = true, name = "tileset")
    private List<TilesetElement> tilesets;

    @ElementList(inline = true, name = "layer")
    private List<LayerElement> layers;

    @Element(name = "objectgroup")
    private ObjectGroupElement objectGroup;

    public String getVersion()
    {
        return version;
    }

    public String getTiledVersion()
    {
        return tiledVersion;
    }

    public String getOrientation()
    {
        return orientation;
    }

    public String getRenderorder()
    {
        return renderorder;
    }

    public int getCompressionlevel()
    {
        return compressionlevel;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getTilewidth()
    {
        return tilewidth;
    }

    public int getTileheight()
    {
        return tileheight;
    }

    public int getInfinite()
    {
        return infinite;
    }

    public int getNextlayerid()
    {
        return nextlayerid;
    }

    public int getNextobjectid()
    {
        return nextobjectid;
    }

    public List<TilesetElement> getTilesets()
    {
        return tilesets;
    }

    public List<LayerElement> getLayers()
    {
        return layers;
    }

    public ObjectGroupElement getObjectGroup()
    {
        return objectGroup;
    }
}
