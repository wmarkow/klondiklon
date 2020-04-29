package com.github.wmarkow.klondiklon.tiled;

import com.github.wmarkow.klondiklon.tiled.tmx.ObjectElement;

public class TmxObject
{
    private ObjectElement objectElement;
    private TmxTiledMap parentMap;

    TmxObject(ObjectElement objectElement, TmxTiledMap parentMap) {
        this.objectElement = objectElement;
        this.parentMap = parentMap;
    }

    public Tile getTile()
    {
        int tileGid = objectElement.getGid();

        for (Tileset tileset : parentMap.getTilests())
        {
            Tile tile = tileset.getTile(tileGid);
            if (tile != null)
            {
                return tile;
            }
        }

        return null;
    }

    public int getId()
    {
        return objectElement.getId();
    }

    public double getX()
    {
        return objectElement.getX();
    }

    public double getY()
    {
        return objectElement.getY();
    }
}
