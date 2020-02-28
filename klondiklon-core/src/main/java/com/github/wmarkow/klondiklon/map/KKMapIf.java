package com.github.wmarkow.klondiklon.map;

import com.github.wmarkow.klondiklon.map.objects.KKMapObjectIf;

public interface KKMapIf
{
    int getHeightInTiles();

    int getTileHeightInPixels();

    KKMapObjectIf[] getObjects();

    void removeObject(KKMapObjectIf object);
    
    org.mapeditor.core.Map getTmxMap();
}
