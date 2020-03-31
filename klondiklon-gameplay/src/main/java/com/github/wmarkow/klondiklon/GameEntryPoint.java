package com.github.wmarkow.klondiklon;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.graphics.FontsRegistrar;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
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
import com.github.wmarkow.klondiklon.music.MusicsRegistrar;
import com.github.wmarkow.klondiklon.resources.graphics.FontsManager;
import com.github.wmarkow.klondiklon.sounds.SoundsRegistrar;
import com.github.wmarkow.klondiklon.ui.tools.GrubbingInteractiveTool;
import com.github.wmarkow.klondiklon.ui.tools.HarvestInteractiveTools;
import com.github.wmarkow.klondiklon.ui.tools.MoveObjectInteractiveTool;
import com.github.wmarkow.klondiklon.ui.tools.SeedInteractiveTool;

public class GameEntryPoint extends ApplicationAdapter
{
    private static Logger LOGGER = LoggerFactory.getLogger(GameEntryPoint.class);

    private SpriteBatch batch;
    private KKMapRenderer renderer;
    private CoordinateCalculator coordinateCalculator;

    private GrubbingInteractiveTool grubbingInteractiveTool;
    private MoveObjectInteractiveTool moveObjectInteractiveTool;
    private HarvestInteractiveTools sickleInteractiveTools;
    private SeedInteractiveTool sowInteractiveTools;

    private FontsManager fontsManager;

    @Override
    public void create()
    {
        fontsManager = ServiceRegistry.getInstance().getFontsManager();
        initDefaultResources();

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
        final OrthographicCamera camera = ServiceRegistry.getInstance().getCamera();

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
        Camera camera = ServiceRegistry.getInstance().getCamera();
        EventBus eventBus = ServiceRegistry.getInstance().getEventBus();

        grubbingInteractiveTool = new GrubbingInteractiveTool(eventBus, kkMap, camera,
                GameplayService.getInstance().getPlayer(),
                GameplayService.getInstance().getObjectTypeDescriptorsManager());
        moveObjectInteractiveTool = new MoveObjectInteractiveTool(eventBus, kkMap, camera,
                GameplayService.getInstance().getObjectTypeDescriptorsManager());
        sickleInteractiveTools = new HarvestInteractiveTools(eventBus, kkMap, camera);
        sowInteractiveTools = new SeedInteractiveTool(eventBus, kkMap, camera);
    }

    private void initDefaultResources()
    {
        FontsRegistrar fontsRegistrar = new FontsRegistrar();
        fontsRegistrar.register(fontsManager);

        TexturesRegistrar texturesRegistrar = new TexturesRegistrar();
        texturesRegistrar.register(ServiceRegistry.getInstance().getTexturesManager());

        MusicsRegistrar musicsRegistrar = new MusicsRegistrar();
        musicsRegistrar.register(ServiceRegistry.getInstance().getMusicManager());

        SoundsRegistrar soundsRegistrar = new SoundsRegistrar();
        soundsRegistrar.register(ServiceRegistry.getInstance().getSoundManager());
    }
}