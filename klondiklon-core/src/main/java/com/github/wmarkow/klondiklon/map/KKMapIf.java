package com.github.wmarkow.klondiklon.map;

import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.map.objects.KKMapObjectIf;

public interface KKMapIf
{
    int getHeightInTiles();

    int getTileHeightInPixels();

    KKMapObjectIf[] getObjects();

    void removeObject(KKMapObjectIf object);
    
    void setObjectCoordinates(KKMapObjectIf object, GdxWorldOrthoCoordinates newCoordinates);
    
    org.mapeditor.core.Map getTmxMap();
}
