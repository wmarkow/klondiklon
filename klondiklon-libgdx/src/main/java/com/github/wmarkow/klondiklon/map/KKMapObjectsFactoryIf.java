package com.github.wmarkow.klondiklon.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.github.wmarkow.klondiklon.map.objects.KKMapObject;

public interface KKMapObjectsFactoryIf
{
    public KKMapObject create(TiledMapTile tile, int id, String objectType);
}
