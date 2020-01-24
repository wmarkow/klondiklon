package com.github.wmarkow.klondiklon.map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

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

    public Vector3 screen2World(Camera camera, int screenX, int screenY)
    {
        return camera.unproject(new Vector3(screenX, screenY, 0));
    }

    public Vector3 world2TmxOrthogonal(int tileMapHeightInTiles, int tileHeightInPixels, Vector3 worldCoordinates)
    {
        final int mapHeight = tileMapHeightInTiles * tileHeightInPixels;

        return new Vector3(worldCoordinates.x, (float) (mapHeight / 2.0 - worldCoordinates.y), 0);
    }
    
    public Vector3 tmxOrthogonal2world(int tileMapHeightInTiles, int tileHeightInPixels, Vector3 tmxOrthogonal)
    {
        final int mapHeight = tileMapHeightInTiles * tileHeightInPixels;
        
        return new Vector3(tmxOrthogonal.x, (float)(mapHeight / 2.0 - tmxOrthogonal.y), 0);
    }

    public Vector3 world2iso(Vector3 world)
    {
        Vector3 vector = new Vector3(world);
        vector.mul(invIsotransform);

        return vector;
    }

    public Vector3 tmxOrthogonal2TmxIso(int tileMapHeightInTiles, int tileHeightInPixels, Vector3 tmxOrthogonal)
    {
        final int mapHeight = tileMapHeightInTiles * tileHeightInPixels;

        double x = tmxOrthogonal.x / 2.0 + tmxOrthogonal.y - mapHeight / 2.0;
        double y = tmxOrthogonal.y - tmxOrthogonal.x / 2.0 + mapHeight / 2.0;

        return new Vector3((float) x, (float) y, (float) 0.0);
    }

    public Vector3 tmxIso2tmxOrthogonal(int tileMapHeightInTiles, int tileHeightInPixels, Vector3 tmxIso)
    {
        final int mapHeight = tileMapHeightInTiles * tileHeightInPixels;

        final double x = tmxIso.x - tmxIso.y + mapHeight;
        final double y = (tmxIso.x + tmxIso.y) / 2.0;

        return new Vector3((float) x, (float) y, 0.0f);
    }
}
