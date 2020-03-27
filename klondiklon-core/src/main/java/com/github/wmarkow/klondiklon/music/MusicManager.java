package com.github.wmarkow.klondiklon.music;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.audio.Music;

public class MusicManager
{
    private Map<String, Music> musics = new HashMap<String, Music>();

    public MusicManager() {
    }

    public void registerMusic(String name, Music music)
    {
        musics.put(name, music);
    }

    public Music getMusic(String name)
    {
        return musics.get(name);
    }
}
