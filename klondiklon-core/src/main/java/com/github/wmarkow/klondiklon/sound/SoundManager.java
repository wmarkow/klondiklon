package com.github.wmarkow.klondiklon.sound;

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

    // FIXME: this is not wanted here
    public void play(Sound sound, float soundDurationInSeconds, int playCount)
    {
        SoundPlayer soundPlayer = new SoundPlayer();
        soundPlayer.play(sound, soundDurationInSeconds, playCount);
    }

    public void play(Sound sound, float soundDurationInSeconds, int playCount, SoundPlayerListener listener)
    {
        SoundPlayer soundPlayer = new SoundPlayer();
        soundPlayer.play(sound, soundDurationInSeconds, playCount, listener);
    }
}
