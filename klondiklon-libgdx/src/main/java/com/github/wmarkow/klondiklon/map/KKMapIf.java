package com.github.wmarkow.klondiklon.map;

import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.map.objects.KKMapObjectIf;
import com.github.wmarkow.klondiklon.tiled.TmxTiledMap;

public interface KKMapIf
{
    int getHeightInTiles();

    int getTileHeightInPixels();

    KKMapObjectIf[] getObjects();

    void removeObject(KKMapObjectIf object);
    
    KKMapObjectIf getObject(int id);
    
    void setObjectCoordinates(KKMapObjectIf object, GdxWorldOrthoCoordinates newCoordinates);
    
    TmxTiledMap getTmxTiledMap();
}
