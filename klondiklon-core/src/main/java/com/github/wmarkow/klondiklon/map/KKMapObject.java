package com.github.wmarkow.klondiklon.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.wmarkow.klondiklon.HomeLand;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;

class KKMapObject extends TiledMapTileMapObject implements KKMapObjectIf
{
    public KKMapObject(TiledMapTile tile, boolean flipHorizontally, boolean flipVertically) {
        super(tile, flipHorizontally, flipVertically);
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
        ShaderProgram currentShader = batch.getShader();
        batch.setShader(HomeLand.SHADER_OUTLINE);
        batch.draw(getTextureRegion().getTexture(), spriteVertices, 0, count);
        batch.setShader(currentShader);
        batch.draw(getTextureRegion().getTexture(), spriteVertices, 0, count);
    }

}
