package com.github.wmarkow.klondiklon.graphics;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class FontsManager
{
    public BitmapFont DEFAULT_FONT;
    private GlyphLayout layout = new GlyphLayout();

    public void init()
    {
        DEFAULT_FONT = new BitmapFont();
    }

    public Dimension meassureText(BitmapFont font, String text)
    {
        layout.setText(font, text);

        return new Dimension(layout.width, layout.height);
    }
}
