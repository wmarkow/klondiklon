package com.github.wmarkow.klondiklon.map.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;

public interface KKMapObjectIf
{
    final static String PROPERTY_TYPE_KEY = "TYPE";

    boolean containsPoint(GdxWorldOrthoCoordinates point);

    float getX();

    float getY();

    float getWidth();

    float getHeight();

    boolean isSelected();

    void setSelected(boolean selected);

    void setSelectedTrue(String tooltipText);

    void setSelectedTrueGreenColor();

    void setSelectedTrueRedColor();

    @Deprecated
    void draw(Batch batch, float[] spriteVertices, int offset, int count);
    
    void draw(Batch batch, float layerOpacity, float unitScale);
    
    String getObjectType();

    int getId();
}
