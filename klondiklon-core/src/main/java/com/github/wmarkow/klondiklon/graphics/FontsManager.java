package com.github.wmarkow.klondiklon.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontsManager
{
    public BitmapFont DEFAULT_FONT;
    public BitmapFont GRUBBING_FONT;
    private GlyphLayout layout = new GlyphLayout();
    private String POLISH_CHARACTERS = "A�BC�DE�FGHIJKL�MN�O�PQRS�TUVWXYZ��a�bc�de�fghijkl�mn�o�pqrs�tuvwxyz��0123456789:";

    public void init()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arialbd.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.characters = POLISH_CHARACTERS;
        parameter.size = 12;
        DEFAULT_FONT = generator.generateFont(parameter);

        parameter.size = 40;
        parameter.color = Color.BLACK;
        GRUBBING_FONT = generator.generateFont(parameter);
    }

    public Dimension meassureText(BitmapFont font, String text)
    {
        layout.setText(font, text);

        return new Dimension(layout.width, layout.height);
    }
}
