package com.github.wmarkow.klondiklon;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.map.KKCameraController;
import com.github.wmarkow.klondiklon.map.KKMap;
import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.map.KKMapRenderer;
import com.github.wmarkow.klondiklon.map.coordinates.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxTouchCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldIsoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.tmx.TmxIsoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.tmx.TmxOrthoCoordinates;
import com.github.wmarkow.klondiklon.resources.graphics.FontsManager;
import com.github.wmarkow.klondiklon.resources.graphics.TexturesManager;
import com.github.wmarkow.klondiklon.resources.music.MusicManager;
import com.github.wmarkow.klondiklon.resources.sound.SoundManager;
import com.github.wmarkow.klondiklon.ui.tools.GrubbingInteractiveTool;
import com.github.wmarkow.klondiklon.ui.tools.HarvestInteractiveTools;
import com.github.wmarkow.klondiklon.ui.tools.MoveObjectInteractiveTool;
import com.github.wmarkow.klondiklon.ui.tools.SeedInteractiveTool;

public class GameEntryPoint extends ApplicationAdapter
{
    private static Logger LOGGER = LoggerFactory.getLogger(GameEntryPoint.class);

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private KKMapRenderer renderer;
    private KKCameraController cameraController;
    private CoordinateCalculator coordinateCalculator;

    private HomeLandLogic homeLandLogic;
    private GrubbingInteractiveTool grubbingInteractiveTool;
    private MoveObjectInteractiveTool moveObjectInteractiveTool;
    private HarvestInteractiveTools sickleInteractiveTools;
    private SeedInteractiveTool sowInteractiveTools;

    private EventBus eventBus;
    private FontsManager fontsManager;
    private TexturesManager texturesManager;
    private MusicManager musicManager;
    private SoundManager soundManager;

    @Override
    public void create()
    {
        eventBus = ServiceRegistry.getInstance().getEventBus();
        fontsManager = ServiceRegistry.getInstance().getFontsManager();
        texturesManager = ServiceRegistry.getInstance().getTexturesManager();
        musicManager = ServiceRegistry.getInstance().getMusicManager();
        soundManager = ServiceRegistry.getInstance().getSoundManager();
        camera = ServiceRegistry.getInstance().getCamera();
        cameraController = ServiceRegistry.getInstance().getCameraController();
        homeLandLogic = new HomeLandLogic();
        homeLandLogic.initFonts(fontsManager);
        homeLandLogic.initTextures(texturesManager);
        homeLandLogic.initMusics(musicManager);
        homeLandLogic.initSounds(soundManager);

        GameplayService.getInstance().loadGameContext();
        // Klondiklon.gameplayService.playMainTheme();
        renderer = new KKMapRenderer((KKMap) GameplayService.getInstance().getCurrentWorldMap());

        coordinateCalculator = new CoordinateCalculator();
        batch = new SpriteBatch();

        GameplayService.getInstance().initUi();
        initInteractiveTools(GameplayService.getInstance().getCurrentWorldMap());
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        batch.begin();

        BitmapFont font = fontsManager.getResource(FontsManager.DEFAULT_FONT);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);

        GdxTouchCoordinates touch = new GdxTouchCoordinates(Gdx.input.getX(), Gdx.input.getY());
        GdxScreenCoordinates screen = coordinateCalculator.touch2Screen(touch);
        GdxWorldOrthoCoordinates world = coordinateCalculator.touch2World(camera, touch);
        KKMapIf currentMap = GameplayService.getInstance().getCurrentWorldMap();
        TmxOrthoCoordinates tmxOrthogonal = coordinateCalculator.world2TmxOrthogonal(currentMap.getHeightInTiles(),
                currentMap.getTileHeightInPixels(), world);
        GdxWorldIsoCoordinates worldIso = coordinateCalculator.world2iso(world);
        TmxIsoCoordinates tmxIso = coordinateCalculator.tmxOrthogonal2TmxIso(currentMap.getHeightInTiles(),
                currentMap.getTileHeightInPixels(), tmxOrthogonal);

        font.draw(batch, String.format("     Touch (x,y): %s, %s", touch.getX(), touch.getY()), 0,
                Gdx.graphics.getHeight() - 0);
        font.draw(batch, String.format("    Screen (x,y): %s, %s", screen.getX(), screen.getY()), 0,
                Gdx.graphics.getHeight() - 20);
        font.draw(batch, String.format("       World (x,y): %s, %s", world.x, world.y), 0,
                Gdx.graphics.getHeight() - 40);
        font.draw(batch, String.format(" World iso (x,y): %s, %s", worldIso.x, worldIso.y), 0,
                Gdx.graphics.getHeight() - 60);
        font.draw(batch, String.format("TMX ortho (x,y): %s, %s", tmxOrthogonal.x, tmxOrthogonal.y), 0,
                Gdx.graphics.getHeight() - 80);
        font.draw(batch, String.format("    TMX iso (x,y): %s, %s", tmxIso.x, tmxIso.y), 0,
                Gdx.graphics.getHeight() - 100);

        batch.end();

        GameplayService.getInstance().getUi().getStage().act();
        GameplayService.getInstance().getUi().getStage().draw();
        GameplayService.getInstance().simulateStep(Instant.now().toEpochMilli());
    }

    @Override
    public void resize(int width, int height)
    {
        GameplayService.getInstance().getUi().getStage().getViewport().update(width, height, true);
    }

    @Override
    public void dispose()
    {
        GameplayService.getInstance().saveGameContext();
    }

    private void initInteractiveTools(KKMapIf kkMap)
    {
        grubbingInteractiveTool = new GrubbingInteractiveTool(eventBus, kkMap, camera,
                GameplayService.getInstance().getPlayer(),
                GameplayService.getInstance().getObjectTypeDescriptorsManager());
        moveObjectInteractiveTool = new MoveObjectInteractiveTool(eventBus, kkMap, camera,
                GameplayService.getInstance().getObjectTypeDescriptorsManager());
        sickleInteractiveTools = new HarvestInteractiveTools(eventBus, kkMap, camera);
        sowInteractiveTools = new SeedInteractiveTool(eventBus, kkMap, camera);
    }
}