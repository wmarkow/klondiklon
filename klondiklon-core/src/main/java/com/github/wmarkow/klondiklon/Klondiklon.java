package com.github.wmarkow.klondiklon;

import com.github.wmarkow.klondiklon.graphics.FontsManager;
import com.github.wmarkow.klondiklon.graphics.ShadersManager;
import com.github.wmarkow.klondiklon.graphics.TexturesManager;
import com.github.wmarkow.klondiklon.music.MusicManager;
import com.github.wmarkow.klondiklon.sound.SoundManager;

public class Klondiklon
{
    public final static TexturesManager texturesManager = new TexturesManager();
    public final static MusicManager musicManager = new MusicManager();
    public final static SoundManager soundManager = new SoundManager();
    public final static ShadersManager shadersManager = new ShadersManager();
    public final static FontsManager fontsManager = new FontsManager();

    public static void init()
    {
        texturesManager.init();
        musicManager.init();
        soundManager.init();
        shadersManager.init();
        fontsManager.init();
    }
}
