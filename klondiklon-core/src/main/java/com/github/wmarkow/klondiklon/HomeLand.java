package com.github.wmarkow.klondiklon;

import java.io.File;

import org.mapeditor.core.Map;
import org.mapeditor.io.TMXMapReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

public class HomeLand extends ApplicationAdapter
{
    private static Logger LOGGER = LoggerFactory.getLogger(HomeLand.class);

    private TiledMapRenderer renderer;
    private OrthographicCamera camera;
    private OrthoCamController cameraController;
    private BitmapFont font;
    private SpriteBatch batch;

    @Override
    public void create()
    {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 10, 10);
        camera.zoom = 2;
        camera.update();

        cameraController = new OrthoCamController(camera);
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

        Tiled2LibGdxMapAdapter libGdxMap = new Tiled2LibGdxMapAdapter(tmxMap);

        renderer = new IsometricTiledMapRenderer(libGdxMap, 1f / 64f);
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
        batch.end();

        if (Gdx.input.isTouched())
        {
            int screenX = Gdx.input.getX();
            int screenY = Gdx.input.getY();

            Vector3 screen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Vector3 world = camera.unproject(screen);

            LOGGER.info(String.format("Screen (x,y) = (%s, %s) to world (x, y, z) = (%s, %s, %s)", screenX, screenY,
                    world.x, world.y, world.z));
        }
    }
}