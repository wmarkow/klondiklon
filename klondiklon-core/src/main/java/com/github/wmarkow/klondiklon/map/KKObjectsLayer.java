package com.github.wmarkow.klondiklon.map;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;

public class KKObjectsLayer extends MapLayer
{
    public KKMapObjectIf[] getMapObjects()
    {
        List<KKMapObjectIf> result = new ArrayList<KKMapObjectIf>();

        for (MapObject mapObject : getObjects())
        {
            if (mapObject instanceof KKMapObjectIf)
            {
                result.add((KKMapObjectIf) mapObject);
            }
        }

        return result.toArray(new KKMapObjectIf[]
        {});
    }
}
