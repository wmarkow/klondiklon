package com.github.wmarkow.klondiklon.map;

import static com.badlogic.gdx.graphics.g2d.Batch.C1;
import static com.badlogic.gdx.graphics.g2d.Batch.C2;
import static com.badlogic.gdx.graphics.g2d.Batch.C3;
import static com.badlogic.gdx.graphics.g2d.Batch.C4;
import static com.badlogic.gdx.graphics.g2d.Batch.U1;
import static com.badlogic.gdx.graphics.g2d.Batch.U2;
import static com.badlogic.gdx.graphics.g2d.Batch.U3;
import static com.badlogic.gdx.graphics.g2d.Batch.U4;
import static com.badlogic.gdx.graphics.g2d.Batch.V1;
import static com.badlogic.gdx.graphics.g2d.Batch.V2;
import static com.badlogic.gdx.graphics.g2d.Batch.V3;
import static com.badlogic.gdx.graphics.g2d.Batch.V4;
import static com.badlogic.gdx.graphics.g2d.Batch.X1;
import static com.badlogic.gdx.graphics.g2d.Batch.X2;
import static com.badlogic.gdx.graphics.g2d.Batch.X3;
import static com.badlogic.gdx.graphics.g2d.Batch.X4;
import static com.badlogic.gdx.graphics.g2d.Batch.Y1;
import static com.badlogic.gdx.graphics.g2d.Batch.Y2;
import static com.badlogic.gdx.graphics.g2d.Batch.Y3;
import static com.badlogic.gdx.graphics.g2d.Batch.Y4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

public class KKIsometricTiledMapRenderer extends IsometricTiledMapRenderer
{
    public KKIsometricTiledMapRenderer(TiledMap map) {
        super(map, 1.0f);
    }

    @Override
    public void renderObjects(MapLayer layer)
    {
        final Color batchColor = batch.getColor();
        final float color = Color.toFloatBits(batchColor.r, batchColor.g, batchColor.b,
                batchColor.a * layer.getOpacity());

        int mapHeight = (int) getMap().getProperties().get("height");
        int mapTileHeight = (int) getMap().getProperties().get("tileheight");

        List<TiledMapTileMapObject> objects = getTiledMapTileMapObjectList(layer.getObjects());
        ByDistanceToTmxIsoOriginComparator comparator = new ByDistanceToTmxIsoOriginComparator(mapHeight,
                mapTileHeight);
        Collections.sort(objects, comparator);

        for (TiledMapTileMapObject tiledMapTileMapObject : objects)
        {
            TextureRegion region = tiledMapTileMapObject.getTextureRegion();

            float x1 = tiledMapTileMapObject.getX() * unitScale;
            float y1 = tiledMapTileMapObject.getY() * unitScale;
            float x2 = x1 + region.getRegionWidth() * unitScale;
            float y2 = y1 + region.getRegionHeight() * unitScale;

            float u1 = region.getU();
            float v1 = region.getV2();
            float u2 = region.getU2();
            float v2 = region.getV();

            vertices[X1] = x1;
            vertices[Y1] = y1;
            vertices[C1] = color;
            vertices[U1] = u1;
            vertices[V1] = v1;

            vertices[X2] = x1;
            vertices[Y2] = y2;
            vertices[C2] = color;
            vertices[U2] = u1;
            vertices[V2] = v2;

            vertices[X3] = x2;
            vertices[Y3] = y2;
            vertices[C3] = color;
            vertices[U3] = u2;
            vertices[V3] = v2;

            vertices[X4] = x2;
            vertices[Y4] = y1;
            vertices[C4] = color;
            vertices[U4] = u2;
            vertices[V4] = v1;

            batch.draw(region.getTexture(), vertices, 0, NUM_VERTICES);
        }
    }

    private List<TiledMapTileMapObject> getTiledMapTileMapObjectList(MapObjects mapObjects)
    {
        List<TiledMapTileMapObject> result = new ArrayList<TiledMapTileMapObject>();

        for (MapObject mapObject : mapObjects)
        {
            if (mapObject == null)
            {
                continue;
            }

            if (!(mapObject instanceof TiledMapTileMapObject))
            {
                continue;
            }

            result.add((TiledMapTileMapObject) mapObject);
        }

        return result;
    }

    private class ByDistanceToTmxIsoOriginComparator implements Comparator<TiledMapTileMapObject>
    {
        private int tileMapHeightInTiles;
        private int tileHeightInPixels;
        private CoordinateCalculator coordinateCalculator = new CoordinateCalculator();

        public ByDistanceToTmxIsoOriginComparator(int tileMapHeightInTiles, int tileHeightInPixels) {
            this.tileMapHeightInTiles = tileMapHeightInTiles;
            this.tileHeightInPixels = tileHeightInPixels;
        }

        @Override
        public int compare(TiledMapTileMapObject o1, TiledMapTileMapObject o2)
        {
            float o1Distance = calculateDistanceToTmxoIsoOrigin(tileMapHeightInTiles, tileHeightInPixels,
                    new Vector3(o1.getX(), o1.getY(), 0));
            float o2Distance = calculateDistanceToTmxoIsoOrigin(tileMapHeightInTiles, tileHeightInPixels,
                    new Vector3(o2.getX(), o2.getY(), 0));

            if (o1Distance < o2Distance)
            {
                return -1;
            }
            if (o1Distance > o2Distance)
            {
                return 1;
            }

            return 0;
        }

        private float calculateDistanceToTmxoIsoOrigin(int tileMapHeightInTiles, int tileHeightInPixels,
                Vector3 worldCoordinates)
        {
            Vector3 tmxIso = coordinateCalculator.world2TmxIso(tileMapHeightInTiles, tileHeightInPixels,
                    worldCoordinates);

            return (float) Math.sqrt(tmxIso.x * tmxIso.x + tmxIso.y * tmxIso.y);
        }
    }
}
