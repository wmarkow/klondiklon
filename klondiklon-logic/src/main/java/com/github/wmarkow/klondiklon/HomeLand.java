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
import com.github.wmarkow.klondiklon.map.KKMap;
import com.github.wmarkow.klondiklon.map.KKMapRenderer;
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

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private KKMapRenderer renderer;
    private KKCameraController cameraController;
    private CoordinateCalculator coordinateCalculator;

    private HomeLandLogic homeLandLogic;
    private GrubbingInteractiveTool grubbingInteractiveTool;

    private Simulation simulation;

    @Override
    public void create()
    {
        KlondiklonCore.init();
        // KlondiklonCore.musicManager.playMainTheme();

        Klondiklon.gameplayService.loadGameContext();
        renderer = new KKMapRenderer((KKMap) Klondiklon.gameplayService.getCurrentWorldMap());

        coordinateCalculator = new CoordinateCalculator();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 10, 10);
        camera.zoom = 2;
        camera.update();

        cameraController = new KKCameraController(camera, KlondiklonCore.eventBus);

        batch = new SpriteBatch();

        homeLandLogic = new HomeLandLogic();
        homeLandLogic.initStorageItemDescriptors(Klondiklon.storageItemDescriptorsManager);
        homeLandLogic.initObjectTypeDescriptors(Klondiklon.objectTypeDescriptorsManager);
        homeLandLogic.initFonts(KlondiklonCore.fontsManager);
        homeLandLogic.initTextures(KlondiklonCore.texturesManager);

        initUi();
        initInteractiveTools(Klondiklon.gameplayService.getCurrentWorldMap());
        initSimulation(Klondiklon.gameplayService.getPlayer());
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
        KKMapIf currentMap = Klondiklon.gameplayService.getCurrentWorldMap();
        TmxOrthoCoordinates tmxOrthogonal = coordinateCalculator.world2TmxOrthogonal(currentMap.getHeightInTiles(),
                currentMap.getTileHeightInPixels(), world);
        GdxWorldIsoCoordinates worldIso = coordinateCalculator.world2iso(world);
        TmxIsoCoordinates tmxIso = coordinateCalculator.tmxOrthogonal2TmxIso(currentMap.getHeightInTiles(),
                currentMap.getTileHeightInPixels(), tmxOrthogonal);

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

    @Override
    public void dispose()
    {
        Klondiklon.gameplayService.saveGameContext();
    }

    private void initSimulation(Player player)
    {
        long epochMilli = Instant.now().toEpochMilli();

        simulation = new Simulation(epochMilli);
        simulation.addSimulable(new RestoreEnergySimulationProcess(player));
    }

    private void initUi()
    {
        Klondiklon.ui = new KKUi(Klondiklon.gameplayService.getPlayer(), KlondiklonCore.eventBus);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(Klondiklon.ui.getStage());
        multiplexer.addProcessor(cameraController);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void initInteractiveTools(KKMapIf kkMap)
    {
        grubbingInteractiveTool = new GrubbingInteractiveTool(KlondiklonCore.eventBus, kkMap, camera,
                Klondiklon.gameplayService.getPlayer(), Klondiklon.objectTypeDescriptorsManager);
    }
}