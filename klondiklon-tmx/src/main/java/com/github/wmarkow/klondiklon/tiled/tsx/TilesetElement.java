package com.github.wmarkow.klondiklon.tiled.tsx;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "tileset")
public class TilesetElement
{
    @Attribute(name = "version")
    private String version;

    @Attribute(name = "tiledversion")
    private String tiledversion;

    @Attribute(name = "name")
    private String name;

    @Attribute(name = "tilewidth")
    private int tilewidth;

    @Attribute(name = "tileheight")
    private int tileheight;

    @Attribute(name = "tilecount")
    private int tilecount;

    @Attribute(name = "columns")
    private int columns;

    @Element(name = "image")
    private ImageElement image;

    @Element(name = "tile", required = false)
    private TileElement tile;

    public String getVersion()
    {
        return version;
    }

    public String getTiledversion()
    {
        return tiledversion;
    }

    public String getName()
    {
        return name;
    }

    public int getTilewidth()
    {
        return tilewidth;
    }

    public int getTileheight()
    {
        return tileheight;
    }

    public int getTilecount()
    {
        return tilecount;
    }

    public int getColumns()
    {
        return columns;
    }

    public ImageElement getImage()
    {
        return image;
    }

    public TileElement getTile()
    {
        return tile;
    }
}
