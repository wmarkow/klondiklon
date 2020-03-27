package com.github.wmarkow.klondiklon.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.github.wmarkow.klondiklon.resources.graphics.FontsManager;

public class FontsRegistrar
{
    public final static String GRUBBING_FONT_NAME = "GRUBBING_FONT";

    public void register(FontsManager fontsManager)
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.classpath("fonts/arialbd.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.characters = FontsManager.POLISH_CHARACTERS;

        parameter.size = 40;
        parameter.color = Color.WHITE;
        BitmapFont font = generator.generateFont(parameter);

        fontsManager.registerResource(GRUBBING_FONT_NAME, font);
    }
}
