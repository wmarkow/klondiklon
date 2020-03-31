package com.github.wmarkow.klondiklon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private OrthographicCamera camera;
    private KKCameraController cameraController;

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

    public OrthographicCamera getCamera()
    {
        return camera;
    }

    public KKCameraController getCameraController()
    {
        return cameraController;
    }

    private void init()
    {
        texturesManager.init();
        shadersManager.init();
        fontsManager.init();
        skinsManager.init();

        /* create camera */
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 10, 10);
        camera.zoom = 2;
        camera.update();

        /* create camera controller */
        cameraController = new KKCameraController(camera, eventBus);
    }
}
