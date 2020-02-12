package com.github.wmarkow.klondiklon.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.graphics.Dimension;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;

class KKMapObject extends TiledMapTileMapObject implements KKMapObjectIf
{
    private boolean selected = false;
    private String tooltipText = null;
    private String objectType;

    private static BitmapFont FONT = new BitmapFont();
    static
    {
        FONT.setColor(Color.BLACK);
        FONT.getData().setScale(2.5f);
    }

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
            batch.setShader(Klondiklon.shadersManager.SHADER_OUTLINE);
            batch.draw(getTextureRegion().getTexture(), spriteVertices, 0, count);
            batch.setShader(currentShader);
        }

        batch.draw(getTextureRegion().getTexture(), spriteVertices, 0, count);

        if (isSelected() && tooltipText != null)
        {
            Texture balloon = Klondiklon.texturesManager.BALLOON;
            float balloonX = getX() - balloon.getWidth() / 2;
            float balloonY = getY() + getHeight();
            batch.draw(balloon, balloonX, balloonY);

            Dimension dim = Klondiklon.fontsManager.meassureText(FONT, tooltipText);
            float textWidth = dim.getWidth();
            float textHeight = dim.getHeight();

            FONT.draw(batch, tooltipText, getX() - textWidth / 2,
                    balloonY + balloon.getHeight() / 2 + textHeight * 0.75f);
        }
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
        this.tooltipText = null;
    }

    @Override
    public void setSelectedTrue(String tooltipText)
    {
        this.selected = true;
        this.tooltipText = tooltipText;
    }

    @Override
    public String getObjectType()
    {
        return objectType;
    }
}
