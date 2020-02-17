package com.github.wmarkow.klondiklon.graphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontsManager
{
    public BitmapFont DEFAULT_FONT;
    public final static String POLISH_CHARACTERS = "AĄBCĆDEĘFGHIJKLŁMNŃOÓPQRSŚTUVWXYZŻŹaąbcćdeęfghijklłmnńoópqrsśtuvwxyzżź0123456789:";
    private GlyphLayout layout = new GlyphLayout();

    private Map<String, BitmapFont> fonts = new HashMap<String, BitmapFont>();

    public void init()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arialbd.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.characters = POLISH_CHARACTERS;
        parameter.size = 12;
        DEFAULT_FONT = generator.generateFont(parameter);
    }

    public void registerFont(String name, BitmapFont font)
    {
        fonts.put(name, font);
    }

    public BitmapFont getFont(String name)
    {
        return fonts.get(name);
    }

    public Dimension meassureText(BitmapFont font, String text)
    {
        layout.setText(font, text);

        return new Dimension(layout.width, layout.height);
    }
}
