package com.github.wmarkow.klondiklon.sounds;

import com.badlogic.gdx.Gdx;
import com.github.wmarkow.klondiklon.resources.sound.SoundManager;

public class SoundsRegistrar
{
    public final static String GRUBBING_CHOPPING = "GRUBBING_CHOPPING";
    public final static String GRUBBING_DIGGING = "GRUBBING_DIGGING";
    public final static String GRUBBING_MINING = "GRUBBING_MINING";
    public final static String GARDEN_SEED = "GARDEN_SEED";
    public final static String GARDEN_HARVEST = "GARDEN_HARVEST";

    public void register(SoundManager soundManager)
    {
        soundManager.registerSound(GRUBBING_CHOPPING,
                Gdx.audio.newSound(Gdx.files.classpath("sounds/grubbing_chopping.ogg")));
        soundManager.registerSound(GRUBBING_DIGGING,
                Gdx.audio.newSound(Gdx.files.classpath("sounds/grubbing_digging.ogg")));
        soundManager.registerSound(GRUBBING_MINING,
                Gdx.audio.newSound(Gdx.files.classpath("sounds/grubbing_mining.ogg")));
        soundManager.registerSound(GARDEN_SEED, Gdx.audio.newSound(Gdx.files.classpath("sounds/garden_seed.ogg")));
        soundManager.registerSound(GARDEN_HARVEST,
                Gdx.audio.newSound(Gdx.files.classpath("sounds/garden_harvest.ogg")));
    }
}
