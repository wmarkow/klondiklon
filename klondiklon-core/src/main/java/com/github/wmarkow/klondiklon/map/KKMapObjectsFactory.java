package com.github.wmarkow.klondiklon.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.github.wmarkow.klondiklon.map.objects.KKMapObject;

public class KKMapObjectsFactory implements KKMapObjectsFactoryIf
{

    @Override
    public KKMapObject create(TiledMapTile libGdxTile, int id, String objectType)
    {
        return new KKMapObject(libGdxTile, id, objectType);
    }
}
