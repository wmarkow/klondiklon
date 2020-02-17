package com.github.wmarkow.klondiklon.map.coordinates;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
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

    public GdxWorldOrthoCoordinates screen2World(Camera camera, GdxScreenCoordinates screenCoordinates)
    {
        return new GdxWorldOrthoCoordinates(camera.unproject(screenCoordinates.toVector3()));
    }

    public GdxScreenCoordinates world2Screen(Camera camera, GdxWorldOrthoCoordinates worldCoordinates)
    {
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
