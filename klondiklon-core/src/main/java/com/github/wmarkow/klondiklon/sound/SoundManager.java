package com.github.wmarkow.klondiklon.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager
{
    public Sound GRUBBING_CHOPPING;
    public Sound GRUBBING_DIGGING;
    public Sound GRUBBING_MINING;
    public Sound GARDEN_SEED;
    public Sound GARDEN_HARVEST;

    public void init()
    {
        GRUBBING_CHOPPING = Gdx.audio.newSound(Gdx.files.classpath("sounds/grubbing_chopping.ogg"));
        GRUBBING_DIGGING = Gdx.audio.newSound(Gdx.files.classpath("sounds/grubbing_digging.ogg"));
        GRUBBING_MINING = Gdx.audio.newSound(Gdx.files.classpath("sounds/grubbing_mining.ogg"));
        GARDEN_SEED = Gdx.audio.newSound(Gdx.files.classpath("sounds/garden_seed.ogg"));
        GARDEN_HARVEST = Gdx.audio.newSound(Gdx.files.classpath("sounds/garden_harvest.ogg"));
    }

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
