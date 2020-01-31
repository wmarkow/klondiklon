package com.github.wmarkow.klondiklon.map;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;

public class KKObjectsLayer extends MapLayer
{
    public KKMapObject[] getMapObjects()
    {
        List<KKMapObject> result = new ArrayList<KKMapObject>();

        for (MapObject mapObject : getObjects())
        {
            if (mapObject instanceof KKMapObject)
            {
                result.add((KKMapObject) mapObject);
            }
        }

        return result.toArray(new KKMapObject[]
        {});
    }
}
