package com.github.wmarkow.klondiklon.map.objects;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.github.wmarkow.klondiklon.map.KKMapObjectsFactory;

public class MapObjectsFactory extends KKMapObjectsFactory
{

    @Override
    public KKMapObject create(TiledMapTile libGdxTile, int id, String objectType)
    {
        return super.create(libGdxTile, id, objectType);
    }
}
