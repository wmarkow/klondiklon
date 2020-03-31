package com.github.wmarkow.klondiklon.map;

import org.junit.Test;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class KKMapTest
{
    @Test
    public void checkForAssignableFromKKMapIf()
    {
        KKMap.class.isAssignableFrom(KKMapIf.class);
    }

    @Test
    public void checkForAssignableFromTiledMap()
    {
        KKMap.class.isAssignableFrom(TiledMap.class);
    }
}
