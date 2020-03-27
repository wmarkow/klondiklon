package com.github.wmarkow.klondiklon;

import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.map.KKCameraController;
import com.github.wmarkow.klondiklon.resources.graphics.FontsManager;
import com.github.wmarkow.klondiklon.resources.graphics.ShadersManager;
import com.github.wmarkow.klondiklon.resources.graphics.SkinsManager;
import com.github.wmarkow.klondiklon.resources.graphics.TexturesManager;
import com.github.wmarkow.klondiklon.resources.music.MusicManager;
import com.github.wmarkow.klondiklon.resources.sound.SoundManager;

public class ServiceRegistry
{
    private TexturesManager texturesManager = new TexturesManager();
    private MusicManager musicManager = new MusicManager();
    private SoundManager soundManager = new SoundManager();
    private ShadersManager shadersManager = new ShadersManager();
    private FontsManager fontsManager = new FontsManager();
    private SkinsManager skinsManager = new SkinsManager();
    private EventBus eventBus = new EventBus();
    public KKCameraController cameraController = null;

    private static ServiceRegistry instance = new ServiceRegistry();

    private ServiceRegistry() {
        init();
    }

    public final static ServiceRegistry getInstance()
    {
        return ServiceRegistry.instance;
    }

    public TexturesManager getTexturesManager()
    {
        return texturesManager;
    }

    public MusicManager getMusicManager()
    {
        return musicManager;
    }

    public SoundManager getSoundManager()
    {
        return soundManager;
    }

    public ShadersManager getShadersManager()
    {
        return shadersManager;
    }

    public FontsManager getFontsManager()
    {
        return fontsManager;
    }

    public SkinsManager getSkinsManager()
    {
        return skinsManager;
    }

    public EventBus getEventBus()
    {
        return eventBus;
    }

    private void init()
    {
        texturesManager.init();
        shadersManager.init();
        fontsManager.init();
        skinsManager.init();
    }
}
