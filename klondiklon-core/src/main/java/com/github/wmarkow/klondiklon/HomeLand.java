package com.github.wmarkow.klondiklon;

import java.io.File;

import org.mapeditor.core.Map;
import org.mapeditor.io.TMXMapReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.github.wmarkow.klondiklon.map.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.KKCameraController;
import com.github.wmarkow.klondiklon.map.KKTiledMap;
import com.github.wmarkow.klondiklon.map.KKTiledMapRenderer;

public class HomeLand extends ApplicationAdapter
{
    private static Logger LOGGER = LoggerFactory.getLogger(HomeLand.class);

    private OrthographicCamera camera;
    private BitmapFont font;
    private SpriteBatch batch;

    private KKTiledMap libGdxMap;
    private KKTiledMapRenderer renderer;
    private KKCameraController cameraController;
    private CoordinateCalculator coordinateCalculator;

    @Override
    public void create()
    {
        coordinateCalculator = new CoordinateCalculator();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 10, 10);
        camera.zoom = 2;
        camera.update();

        cameraController = new KKCameraController(camera);
        Gdx.input.setInputProcessor(cameraController);

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

        Vector3 screen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 world = coordinateCalculator.screen2World(camera, Gdx.input.getX(), Gdx.input.getY());
        Vector3 tmxOrthogonal = coordinateCalculator.world2TmxOrthogonal(libGdxMap.getHeightInTiles(),
                libGdxMap.getTileHeightInPixels(), world);
        Vector3 worldIso = coordinateCalculator.world2iso(world);
        Vector3 tmxIso = coordinateCalculator.tmxOrthogonal2TmxIso(libGdxMap.getHeightInTiles(),
                libGdxMap.getTileHeightInPixels(), tmxOrthogonal);

        font.draw(batch, String.format("     Screen (x,y): %s, %s", screen.x, screen.y), 0,
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
    }
}