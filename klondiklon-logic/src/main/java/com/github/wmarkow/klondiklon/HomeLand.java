package com.github.wmarkow.klondiklon;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.map.KKCameraController;
import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.map.KKTiledMap;
import com.github.wmarkow.klondiklon.map.KKTiledMapRenderer;
import com.github.wmarkow.klondiklon.map.coordinates.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldIsoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.tmx.TmxIsoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.tmx.TmxOrthoCoordinates;
import com.github.wmarkow.klondiklon.player.Player;
import com.github.wmarkow.klondiklon.simulation.Simulation;
import com.github.wmarkow.klondiklon.simulation.processes.RestoreEnergySimulationProcess;
import com.github.wmarkow.klondiklon.ui.KKUi;
import com.github.wmarkow.klondiklon.ui.tools.GrubbingInteractiveTool;
import com.github.wmarkow.klondiklon.worlds.WorldsManager;

public class HomeLand extends ApplicationAdapter
{
    private static Logger LOGGER = LoggerFactory.getLogger(HomeLand.class);

    private EventBus eventBus = new EventBus();
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private KKMapIf klondiklonMap;
    private KKTiledMapRenderer renderer;
    private KKCameraController cameraController;
    private CoordinateCalculator coordinateCalculator;

    private HomeLandLogic homeLandLogic;
    private GrubbingInteractiveTool grubbingInteractiveTool;

    private Simulation simulation;
    private WorldsManager worldsManager = new WorldsManager();

    @Override
    public void create()
    {
        KlondiklonCore.init();
        // KlondiklonCore.musicManager.playMainTheme();

        KKTiledMap libGdxMap = readHomeWorld();
        klondiklonMap = (KKMapIf) libGdxMap;
        renderer = new KKTiledMapRenderer(libGdxMap);

        coordinateCalculator = new CoordinateCalculator();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 10, 10);
        camera.zoom = 2;
        camera.update();

        cameraController = new KKCameraController(camera, eventBus);

        batch = new SpriteBatch();

        homeLandLogic = new HomeLandLogic();
        homeLandLogic.initStorageItemDescriptors(Klondiklon.storageItemDescriptorsManager);
        homeLandLogic.initObjectTypeDescriptors(Klondiklon.objectTypeDescriptorsManager);
        homeLandLogic.initFonts(KlondiklonCore.fontsManager);
        homeLandLogic.initTextures(KlondiklonCore.texturesManager);

        initPlayer();
        initUi();
        initInteractiveTools(libGdxMap);
        initSimulation(Klondiklon.player);
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
        KlondiklonCore.fontsManager.DEFAULT_FONT.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);

        GdxScreenCoordinates screen = new GdxScreenCoordinates(Gdx.input.getX(), Gdx.input.getY());
        GdxWorldOrthoCoordinates world = coordinateCalculator.screen2World(camera, screen);
        TmxOrthoCoordinates tmxOrthogonal = coordinateCalculator.world2TmxOrthogonal(klondiklonMap.getHeightInTiles(),
                klondiklonMap.getTileHeightInPixels(), world);
        GdxWorldIsoCoordinates worldIso = coordinateCalculator.world2iso(world);
        TmxIsoCoordinates tmxIso = coordinateCalculator.tmxOrthogonal2TmxIso(klondiklonMap.getHeightInTiles(),
                klondiklonMap.getTileHeightInPixels(), tmxOrthogonal);

        KlondiklonCore.fontsManager.DEFAULT_FONT.draw(batch,
                String.format("     Screen (x,y): %s, %s", screen.getX(), screen.getY()), 0,
                Gdx.graphics.getHeight() - 0);
        KlondiklonCore.fontsManager.DEFAULT_FONT.draw(batch,
                String.format("       World (x,y): %s, %s", world.x, world.y), 0, Gdx.graphics.getHeight() - 20);
        KlondiklonCore.fontsManager.DEFAULT_FONT.draw(batch,
                String.format(" World iso (x,y): %s, %s", worldIso.x, worldIso.y), 0, Gdx.graphics.getHeight() - 40);
        KlondiklonCore.fontsManager.DEFAULT_FONT.draw(batch,
                String.format("TMX ortho (x,y): %s, %s", tmxOrthogonal.x, tmxOrthogonal.y), 0,
                Gdx.graphics.getHeight() - 60);
        KlondiklonCore.fontsManager.DEFAULT_FONT.draw(batch,
                String.format("    TMX iso (x,y): %s, %s", tmxIso.x, tmxIso.y), 0, Gdx.graphics.getHeight() - 80);

        batch.end();

        Klondiklon.ui.getStage().act();
        Klondiklon.ui.getStage().draw();
        simulation.simulateStep(Instant.now().toEpochMilli());
    }

    @Override
    public void resize(int width, int height)
    {
        Klondiklon.ui.getStage().getViewport().update(width, height, true);
    }

    private void initSimulation(Player player)
    {
        long epochMilli = Instant.now().toEpochMilli();

        simulation = new Simulation(epochMilli);
        simulation.addSimulable(new RestoreEnergySimulationProcess(player));
    }

    private KKTiledMap readHomeWorld()
    {
        worldsManager.copyHomeWorldFromClasspathToInternal();
        return worldsManager.readHomeWorld();
    }

    private void initPlayer()
    {
        Player player = new Player(eventBus);
        Klondiklon.player = player;
    }

    private void initUi()
    {
        Klondiklon.ui = new KKUi(Klondiklon.player, eventBus);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(Klondiklon.ui.getStage());
        multiplexer.addProcessor(cameraController);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void initInteractiveTools(KKTiledMap libGdxMap)
    {
        grubbingInteractiveTool = new GrubbingInteractiveTool(eventBus, libGdxMap, camera, Klondiklon.player,
                Klondiklon.objectTypeDescriptorsManager);
    }
}