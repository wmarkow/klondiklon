package com.github.wmarkow.klondiklon;

import java.io.File;

import org.mapeditor.core.Map;
import org.mapeditor.io.TMXMapReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.map.KKCameraController;
import com.github.wmarkow.klondiklon.map.KKTiledMap;
import com.github.wmarkow.klondiklon.map.KKTiledMapRenderer;
import com.github.wmarkow.klondiklon.map.coordinates.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldIsoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.tmx.TmxIsoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.tmx.TmxOrthoCoordinates;
import com.github.wmarkow.klondiklon.ui.KKUi;

public class HomeLand extends ApplicationAdapter
{
    private static Logger LOGGER = LoggerFactory.getLogger(HomeLand.class);

    private EventBus eventBus = new EventBus();

    private OrthographicCamera camera;
    private BitmapFont font;
    private SpriteBatch batch;

    private KKTiledMap libGdxMap;
    private KKTiledMapRenderer renderer;
    private KKCameraController cameraController;
    private CoordinateCalculator coordinateCalculator;

    private HomeLandLogic homeLandLogic;
    private GrubbingInteractiveTool grubbingInteractiveTool;

    public static ShaderProgram SHADER_OUTLINE;
    public static Sound GRUBBING_CHOPPING;
    public static Sound GRUBBING_DIGGING;
    public static Sound GRUBBING_MINING;

    private KKUi klondiklonUi;

    @Override
    public void create()
    {
        loadShaders();
        loadSounds();

        coordinateCalculator = new CoordinateCalculator();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 10, 10);
        camera.zoom = 2;
        camera.update();

        cameraController = new KKCameraController(camera, eventBus);

        font = new BitmapFont();
        batch = new SpriteBatch();

        File file = new File(
                "C:\\Users\\wmarkowski\\dev-test\\sources\\java\\klondiklon\\klondiklon-core\\src\\main\\resources\\home.tmx");
        TMXMapReader tmxMapReader = new TMXMapReader();
        Map tmxMap = null;
        try
        {
            tmxMap = tmxMapReader.readMap(file.getAbsolutePath());
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        libGdxMap = new KKTiledMap(tmxMap);

        renderer = new KKTiledMapRenderer(libGdxMap);

        homeLandLogic = new HomeLandLogic();
        grubbingInteractiveTool = new GrubbingInteractiveTool(eventBus, libGdxMap, camera);

        klondiklonUi = new KKUi();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(klondiklonUi.getStage());
        multiplexer.addProcessor(cameraController);
        Gdx.input.setInputProcessor(multiplexer);
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
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);

        GdxScreenCoordinates screen = new GdxScreenCoordinates(Gdx.input.getX(), Gdx.input.getY());
        GdxWorldOrthoCoordinates world = coordinateCalculator.screen2World(camera, screen);
        TmxOrthoCoordinates tmxOrthogonal = coordinateCalculator.world2TmxOrthogonal(libGdxMap.getHeightInTiles(),
                libGdxMap.getTileHeightInPixels(), world);
        GdxWorldIsoCoordinates worldIso = coordinateCalculator.world2iso(world);
        TmxIsoCoordinates tmxIso = coordinateCalculator.tmxOrthogonal2TmxIso(libGdxMap.getHeightInTiles(),
                libGdxMap.getTileHeightInPixels(), tmxOrthogonal);

        font.draw(batch, String.format("     Screen (x,y): %s, %s", screen.getX(), screen.getY()), 0,
                Gdx.graphics.getHeight() - 0);
        font.draw(batch, String.format("       World (x,y): %s, %s", world.x, world.y), 0,
                Gdx.graphics.getHeight() - 20);
        font.draw(batch, String.format(" World iso (x,y): %s, %s", worldIso.x, worldIso.y), 0,
                Gdx.graphics.getHeight() - 40);
        font.draw(batch, String.format("TMX ortho (x,y): %s, %s", tmxOrthogonal.x, tmxOrthogonal.y), 0,
                Gdx.graphics.getHeight() - 60);
        font.draw(batch, String.format("    TMX iso (x,y): %s, %s", tmxIso.x, tmxIso.y), 0,
                Gdx.graphics.getHeight() - 80);

        batch.end();

        klondiklonUi.getStage().act();
        klondiklonUi.getStage().draw();
    }

    private void loadShaders()
    {
        String vertexShader;
        String fragmentShader;
        vertexShader = Gdx.files.classpath("shaders/outline_vertex.glsl").readString();
        fragmentShader = Gdx.files.classpath("shaders/outline_fragment.glsl").readString();
        SHADER_OUTLINE = new ShaderProgram(vertexShader, fragmentShader);
        if (!SHADER_OUTLINE.isCompiled())
        {
            throw new GdxRuntimeException("Couldn't compile shader: " + SHADER_OUTLINE.getLog());
        }

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        HomeLand.SHADER_OUTLINE.begin();
        HomeLand.SHADER_OUTLINE.setUniformf("u_viewportInverse", new Vector2(1f / width, 1f / height));
        HomeLand.SHADER_OUTLINE.setUniformf("u_offset", 10);
        HomeLand.SHADER_OUTLINE.setUniformf("u_step", Math.min(1f, width / 70f));
        HomeLand.SHADER_OUTLINE.setUniformf("u_color", new Vector3(0.0f, 1.0f, 1.0f));
        HomeLand.SHADER_OUTLINE.end();
    }

    private void loadSounds()
    {
        GRUBBING_CHOPPING = Gdx.audio.newSound(Gdx.files.classpath("sounds/grubbing_chopping.ogg"));
        GRUBBING_DIGGING = Gdx.audio.newSound(Gdx.files.classpath("sounds/grubbing_digging.ogg"));
        GRUBBING_MINING = Gdx.audio.newSound(Gdx.files.classpath("sounds/grubbing_mining.ogg"));
    }

}