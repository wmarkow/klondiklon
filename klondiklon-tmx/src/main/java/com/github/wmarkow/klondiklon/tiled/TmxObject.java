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

    public TileInfo getTileInfo()
    {
        int tileGid = objectElement.getGid();

        for (Tileset tileset : parentMap.getTilests())
        {
            TileInfo tileInfo = tileset.getTileInfo(tileGid);
            if (tileInfo != null)
            {
                return tileInfo;
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
