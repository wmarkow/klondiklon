package com.github.wmarkow.klondiklon.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager
{
    private Music mainTheme;

    public MusicManager() {
    }

    public void init()
    {
        mainTheme = Gdx.audio.newMusic(Gdx.files.internal("music/main_theme.ogg"));
    }

    public void playMainTheme()
    {
        mainTheme.play();
        mainTheme.setLooping(true);
    }
}
