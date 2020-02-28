package com.github.wmarkow.klondiklon;

import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.graphics.FontsManager;
import com.github.wmarkow.klondiklon.graphics.ShadersManager;
import com.github.wmarkow.klondiklon.graphics.SkinsManager;
import com.github.wmarkow.klondiklon.graphics.TexturesManager;
import com.github.wmarkow.klondiklon.music.MusicManager;
import com.github.wmarkow.klondiklon.sound.SoundManager;

public class KlondiklonCore
{
    public final static TexturesManager texturesManager = new TexturesManager();
    public final static MusicManager musicManager = new MusicManager();
    public final static SoundManager soundManager = new SoundManager();
    public final static ShadersManager shadersManager = new ShadersManager();
    public final static FontsManager fontsManager = new FontsManager();
    public final static SkinsManager skinsManager = new SkinsManager();
    public final static EventBus eventBus = new EventBus();
    
    public static void init()
    {
        texturesManager.init();
        musicManager.init();
        soundManager.init();
        shadersManager.init();
        fontsManager.init();
        skinsManager.init();
    }
}
