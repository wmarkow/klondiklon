package com.github.wmarkow.klondiklon.map.coordinates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxTouchCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldIsoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.tmx.TmxIsoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.tmx.TmxOrthoCoordinates;

public class CoordinateCalculator
{
    private Matrix4 isoTransform;
    private Matrix4 invIsotransform;

    public CoordinateCalculator() {
        // create the isometric transform
        isoTransform = new Matrix4();
        isoTransform.idt();

        isoTransform.scale((float) (Math.sqrt(2.0) / 2.0), (float) (Math.sqrt(2.0) / 4.0), 1.0f);
        isoTransform.rotate(0.0f, 0.0f, 1.0f, -45);

        // ... and the inverse matrix
        invIsotransform = new Matrix4(isoTransform);
        invIsotransform.inv();
    }

    public GdxScreenCoordinates touch2Screen(GdxTouchCoordinates touchCoordinates)
    {
        int screenX = touchCoordinates.getX();
        int screenY = Gdx.graphics.getHeight() - 1 - touchCoordinates.getY();

        return new GdxScreenCoordinates(screenX, screenY);
    }

    public GdxWorldOrthoCoordinates touch2World(Camera camera, GdxTouchCoordinates touchCoordinates)
    {
        /***
         * Java doc from {@link Camera#unproject}: Function to translate a point given
         * in screen coordinates to world space. It's the same as GLU gluUnProject but
         * does not rely on OpenGL. The viewport is assumed to span the whole screen and
         * is fetched from Graphics.getWidth() and Graphics.getHeight(). The x- and
         * y-coordinate of vec are assumed to be in screen coordinates (origin is the
         * top left corner, y pointing down, x pointing to the right) as reported by the
         * touch methods in Input. A z-coordinate of 0 will return a point on the near
         * plane, a z-coordinate of 1 will return a point on the far plane.
         * <p>
         * So it converts from GdxTouchCoordinates to GdxWorldOrthoCoordinates
         * </p>
         */
        return new GdxWorldOrthoCoordinates(camera.unproject(touchCoordinates.toVector3()));
    }

    public GdxScreenCoordinates world2Screen(Camera camera, GdxWorldOrthoCoordinates worldCoordinates)
    {
        /***
         * Java doc from {@link Camera#project}: Projects the Vector3 given in world
         * space to screen coordinates. It's the same as GLU gluProject with one small
         * deviation: The viewport is assumed to span the whole screen. The screen
         * coordinate system has its origin in the bottom left, with the y-axis pointing
         * upwards and the x-axis pointing to the right. This makes it easily useable in
         * conjunction with Batch and similar classes.
         * <p>
         * So it converts from GdxWorldOrthoCoordinates to GdxScreenCoordinates
         * </p>
         */
        Vector3 result = camera.project(worldCoordinates);

        return new GdxScreenCoordinates((int) result.x, (int) result.y);
    }

    public TmxOrthoCoordinates world2TmxOrthogonal(int tileMapHeightInTiles, int tileHeightInPixels,
            GdxWorldOrthoCoordinates world)
    {
        final int mapHeight = tileMapHeightInTiles * tileHeightInPixels;

        return new TmxOrthoCoordinates(world.x, (float) (mapHeight / 2.0 - world.y), 0);
    }

    public GdxWorldOrthoCoordinates tmxOrthogonal2world(int tileMapHeightInTiles, int tileHeightInPixels,
            TmxOrthoCoordinates tmxOrthogonal)
    {
        final int mapHeight = tileMapHeightInTiles * tileHeightInPixels;

        return new GdxWorldOrthoCoordinates(tmxOrthogonal.x, (float) (mapHeight / 2.0 - tmxOrthogonal.y), 0);
    }

    public GdxWorldIsoCoordinates world2iso(GdxWorldOrthoCoordinates world)
    {
        Vector3 vector = new Vector3(world);
        vector.mul(invIsotransform);

        return new GdxWorldIsoCoordinates(vector);
    }

    public TmxIsoCoordinates tmxOrthogonal2TmxIso(int tileMapHeightInTiles, int tileHeightInPixels,
            TmxOrthoCoordinates tmxOrthogonal)
    {
        final int mapHeight = tileMapHeightInTiles * tileHeightInPixels;

        double x = tmxOrthogonal.x / 2.0 + tmxOrthogonal.y - mapHeight / 2.0;
        double y = tmxOrthogonal.y - tmxOrthogonal.x / 2.0 + mapHeight / 2.0;

        return new TmxIsoCoordinates((float) x, (float) y, (float) 0.0);
    }

    public TmxOrthoCoordinates tmxIso2tmxOrthogonal(int tileMapHeightInTiles, int tileHeightInPixels,
            TmxIsoCoordinates tmxIso)
    {
        final int mapHeight = tileMapHeightInTiles * tileHeightInPixels;

        final double x = tmxIso.x - tmxIso.y + mapHeight;
        final double y = (tmxIso.x + tmxIso.y) / 2.0;

        return new TmxOrthoCoordinates((float) x, (float) y, 0.0f);
    }

    public TmxIsoCoordinates world2TmxIso(int tileMapHeightInTiles, int tileHeightInPixels,
            GdxWorldOrthoCoordinates world)
    {
        TmxOrthoCoordinates tmxOrthogonal = world2TmxOrthogonal(tileMapHeightInTiles, tileHeightInPixels, world);
        return tmxOrthogonal2TmxIso(tileMapHeightInTiles, tileHeightInPixels, tmxOrthogonal);
    }
}
