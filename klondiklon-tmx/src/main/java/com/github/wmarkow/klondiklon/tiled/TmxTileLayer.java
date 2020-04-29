package com.github.wmarkow.klondiklon.tiled;

import com.github.wmarkow.klondiklon.tiled.tmx.LayerElement;

public class TmxTileLayer extends TmxLayer
{
    private int tileWidth;
    private int tileHeight;
    private LayerElement layerElement;

    TmxTileLayer(int tileWidth, int tileHeight, TmxTiledMap parentMap, LayerElement layerElement) {
        super(parentMap);

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.layerElement = layerElement;
    }

    @Override
    public String getName()
    {
        return layerElement.getName();
    }

    public int getWidth()
    {
        return layerElement.getWidth();
    }

    public int getHeight()
    {
        return layerElement.getHeight();
    }

    public int getTileWidth()
    {
        return tileWidth;
    }

    public int getTileHeight()
    {
        return tileHeight;
    }

    public Tile getTileAt(int x, int y)
    {
        int tileGid = getTileGidAt(x, y);

        for (Tileset tileset : getParentMap().getTilests())
        {
            Tile tile = tileset.getTile(tileGid);
            if (tile != null)
            {
                return tile;
            }
        }

        return null;
    }

    private int getTileGidAt(int x, int y)
    {
        if (!"csv".equals(layerElement.getData().getEncoding()))
        {
            throw new IllegalArgumentException(
                    String.format("Unsupported layer data encoding: %s", layerElement.getData().getEncoding()));
        }

        String dataAsString = layerElement.getData().getData();
        String[] rows = dataAsString.split("\n");
        // first row is always empty
        String rowY = rows[y + 1];
        String tileGid = rowY.split(",")[x].trim();

        return Integer.valueOf(tileGid);
    }
}
