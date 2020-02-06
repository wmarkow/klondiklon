package com.github.wmarkow.klondiklon.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.github.wmarkow.klondiklon.HomeLand;
import com.github.wmarkow.klondiklon.ObjectTypes;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;

class KKMapObject extends TiledMapTileMapObject implements KKMapObjectIf
{
    private boolean selected = false;
    private String objectType;

    public KKMapObject(TiledMapTile tile, String objectType) {
        super(tile, false, false);
        
        this.objectType = objectType;
    }

    @Override
    public float getWidth()
    {
        return getTextureRegion().getRegionWidth();
    }

    @Override
    public float getHeight()
    {
        return getTextureRegion().getRegionHeight();
    }

    /***
     * Checks if the specific point is in the bounds of this object. Takes into
     * account that the anchor of the object is a BOTTOM_MIDLE.
     * 
     * @param point
     *            - but only X and Y are take into account
     * @return
     */
    public boolean containsPoint(GdxWorldOrthoCoordinates point)
    {
        float pointX = point.x;
        float pointY = point.y;

        float x = getX();
        float y = getY();
        float width = getTextureRegion().getRegionWidth();
        float height = getTextureRegion().getRegionHeight();

        if (pointX < x - width / 2.0)
        {
            return false;
        }
        if (pointX > x + width / 2.0)
        {
            return false;
        }

        if (pointY < y)
        {
            return false;
        }
        if (pointY > y + height)
        {
            return false;
        }

        return true;
    }

    @Override
    public void draw(Batch batch, float[] spriteVertices, int offset, int count)
    {
        if (isSelected())
        {
            ShaderProgram currentShader = batch.getShader();
            batch.setShader(HomeLand.SHADER_OUTLINE);
            batch.draw(getTextureRegion().getTexture(), spriteVertices, 0, count);
            batch.setShader(currentShader);
        }
        
        batch.draw(getTextureRegion().getTexture(), spriteVertices, 0, count);
    }

    @Override
    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    @Override
    public String getObjectType()
    {
        return objectType;
    }
}
