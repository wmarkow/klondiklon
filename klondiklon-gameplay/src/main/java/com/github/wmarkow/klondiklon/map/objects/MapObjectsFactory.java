package com.github.wmarkow.klondiklon.map.objects;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.github.wmarkow.klondiklon.map.KKMapObjectsFactory;
import com.github.wmarkow.klondiklon.objects.ObjectTypes;

public class MapObjectsFactory extends KKMapObjectsFactory
{

    @Override
    public KKMapObject create(TiledMapTile libGdxTile, int id, String objectType)
    {
        if (ObjectTypes.GARDEN.equals(objectType))
        {
            return new GardenCellObject(libGdxTile, id, objectType);
        }

        return super.create(libGdxTile, id, objectType);
    }
}
