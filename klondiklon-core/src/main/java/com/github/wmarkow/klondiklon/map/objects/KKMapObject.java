package com.github.wmarkow.klondiklon.map.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.github.wmarkow.klondiklon.KlondiklonCore;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;

public class KKMapObject extends TiledMapTileMapObject implements KKMapObjectIf
{
    private boolean selected = false;
    private String tooltipText = null;
    private String objectType;
    private int id;

    private static BitmapFont FONT = new BitmapFont();
    static
    {
        FONT.setColor(Color.BLACK);
        FONT.getData().setScale(2.5f);
    }

    public KKMapObject(TiledMapTile tile, int id, String objectType) {
        super(tile, false, false);

        this.id = id;
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
            batch.setShader(KlondiklonCore.shadersManager.SHADER_OUTLINE);
            batch.draw(getTextureRegion().getTexture(), spriteVertices, 0, count);
            batch.setShader(currentShader);
        }

        batch.draw(getTextureRegion().getTexture(), spriteVertices, 0, count);

        if (isSelected() && tooltipText != null)
        {
            // TODO: not a nice idea to use this font name. It is better to create something
            // like "tooltip provider"
            BitmapFont font = KlondiklonCore.fontsManager.getFont("GRUBBING_FONT");

            // TODO: not a nice idea to use this font name. It is better to create something
            // like "tooltip provider"
            NinePatch balloonNinePatch = new NinePatch(KlondiklonCore.texturesManager.getTexture("BALLOON_BLUE"), 14,
                    14, 14, 27);
            NinePatchDrawable balloonDrawable = new NinePatchDrawable(balloonNinePatch);
            TextButtonStyle style = new TextButtonStyle(balloonDrawable, balloonDrawable, balloonDrawable, font);
            style.fontColor = Color.WHITE;

            TextButton button = new TextButton(tooltipText, style);

            button.setX(getX() - button.getWidth() / 2);
            button.setY(getY() + getHeight());
            button.draw(batch, 1.0f);
        }
    }

    public TextureRegion getTextureRegion()
    {
        return this.getTile().getTextureRegion();
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

    @Override
    public int getId()
    {
        return id;
    }
}
