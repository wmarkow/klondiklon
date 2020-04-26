package com.github.wmarkow.klondiklon.tiled;

public class TmxTileLayer extends TmxLayer
{
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;

    public TmxTileLayer(String name, int width, int height, int tileWidth, int tileHeight) {
        super(name);
        
        this.width = width;
        this.height = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getTileWidth()
    {
        return tileWidth;
    }

    public int getTileHeight()
    {
        return tileHeight;
    }
}
