package com.github.wmarkow.klondiklon.ui.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class ImageWidget extends com.badlogic.gdx.scenes.scene2d.ui.Image
{
    private float scale = 1.0f;

    /** Creates an image with no drawable, stretched, and aligned center. */
    public ImageWidget() {
        super((Drawable) null);
    }

    /**
     * Creates an image stretched, and aligned center.
     * 
     * @param patch
     *            May be null.
     */
    public ImageWidget(NinePatch patch) {
        super(patch);
    }

    /**
     * Creates an image stretched, and aligned center.
     * 
     * @param region
     *            May be null.
     */
    public ImageWidget(TextureRegion region) {
        super(region);
    }

    /** Creates an image stretched, and aligned center. */
    public ImageWidget(Texture texture) {
        super(texture);
    }

    /** Creates an image stretched, and aligned center. */
    public ImageWidget(Skin skin, String drawableName) {
        super(skin, drawableName);
    }

    /**
     * Creates an image stretched, and aligned center.
     * 
     * @param drawable
     *            May be null.
     */
    public ImageWidget(Drawable drawable) {
        super(drawable);
    }

    /**
     * Creates an image aligned center.
     * 
     * @param drawable
     *            May be null.
     */
    public ImageWidget(Drawable drawable, Scaling scaling) {
        super(drawable, scaling);
    }

    /**
     * @param drawable
     *            May be null.
     */
    public ImageWidget(Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
    }

    public float getPrefWidth()
    {
        return super.getPrefWidth() * scale;
    }

    public float getPrefHeight()
    {
        return super.getPrefHeight() * scale;
    }

    public void setScale(float scaleXY)
    {
        this.scale = scaleXY;
        this.scale = scaleXY;
    }
}
