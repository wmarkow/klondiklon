package com.github.wmarkow.klondiklon.map;

import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;

public interface KKMapObjectIf
{
    boolean containsPoint(GdxWorldOrthoCoordinates point);

    float getX();

    float getY();

    float getWidth();

    float getHeight();
}
