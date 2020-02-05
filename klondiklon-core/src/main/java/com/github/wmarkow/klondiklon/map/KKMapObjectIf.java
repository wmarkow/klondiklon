package com.github.wmarkow.klondiklon.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.wmarkow.klondiklon.ObjectType;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;

public interface KKMapObjectIf
{
    boolean containsPoint(GdxWorldOrthoCoordinates point);

    float getX();

    float getY();

    float getWidth();

    float getHeight();

    boolean isSelected();

    void setSelected(boolean selected);

    void draw(Batch batch, float[] spriteVertices, int offset, int count);
    
    String getObjectType();
}
