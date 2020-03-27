package com.github.wmarkow.klondiklon.music;

import com.badlogic.gdx.Gdx;

public class MusicsRegistrar
{
    public final static String MAIN_THEME = "MAIN_THEME";

    public void register(MusicManager musicManager)
    {
        musicManager.registerMusic(MAIN_THEME, Gdx.audio.newMusic(Gdx.files.internal("music/main_theme.ogg")));
    }
}
