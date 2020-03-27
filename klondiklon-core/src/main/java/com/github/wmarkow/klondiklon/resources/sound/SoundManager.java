package com.github.wmarkow.klondiklon.resources.sound;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.audio.Sound;

public class SoundManager
{
    private Map<String, Sound> sounds = new HashMap<String, Sound>();

    public void registerSound(String name, Sound sound)
    {
        sounds.put(name, sound);
    }

    public Sound getSound(String name)
    {
        return sounds.get(name);
    }
}
