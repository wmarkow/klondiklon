package com.github.wmarkow.klondiklon.resources.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.github.wmarkow.klondiklon.graphics.Dimension;
import com.github.wmarkow.klondiklon.resources.ResourcesManager;

public class FontsManager extends ResourcesManager<BitmapFont>
{
    public final static String DEFAULT_FONT = "DEFAULT_FONT";
    public final static String POLISH_CHARACTERS = "AĄBCĆDEĘFGHIJKLŁMNŃOÓPQRSŚTUVWXYZŻŹaąbcćdeęfghijklłmnńoópqrsśtuvwxyzżź0123456789:";
    private GlyphLayout layout = new GlyphLayout();

    public void init()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arialbd.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.characters = POLISH_CHARACTERS;
        parameter.size = 12;
        BitmapFont font = generator.generateFont(parameter);

        this.registerResource(DEFAULT_FONT, font);
    }

    public Dimension meassureText(BitmapFont font, String text)
    {
        layout.setText(font, text);

        return new Dimension(layout.width, layout.height);
    }
}
