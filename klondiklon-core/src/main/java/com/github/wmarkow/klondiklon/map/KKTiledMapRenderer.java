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
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.github.wmarkow.klondiklon.map.coordinates.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldIsoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.map.objects.KKMapObjectIf;

public class KKTiledMapRenderer extends BatchTiledMapRenderer
{
    public KKTiledMapRenderer(KKTiledMap map) {
        super(map, 1.0f);
    }

    private KKTiledMap getKKTiledMap()
    {
        return (KKTiledMap) getMap();
    }

    @Override
    public void renderTileLayer(TiledMapTileLayer layer)
    {
        final Color batchColor = batch.getColor();
        final float color = Color.toFloatBits(batchColor.r, batchColor.g, batchColor.b,
                batchColor.a * layer.getOpacity());

        float tileWidth = layer.getTileWidth() * unitScale;
        float tileHeight = layer.getTileHeight() * unitScale;

        final float layerOffsetX = layer.getRenderOffsetX() * unitScale;
        // offset in tiled is y down, so we flip it
        final float layerOffsetY = -layer.getRenderOffsetY() * unitScale;

        float halfTileWidth = tileWidth * 0.5f;
        float halfTileHeight = tileHeight * 0.5f;

        // setting up the screen points
        GdxWorldOrthoCoordinates topRight = new GdxWorldOrthoCoordinates();
        GdxWorldOrthoCoordinates bottomLeft = new GdxWorldOrthoCoordinates();
        GdxWorldOrthoCoordinates topLeft = new GdxWorldOrthoCoordinates();
        GdxWorldOrthoCoordinates bottomRight = new GdxWorldOrthoCoordinates();
        // COL1
        topRight.set(viewBounds.x + viewBounds.width - layerOffsetX, viewBounds.y - layerOffsetY, 0);
        // COL2
        bottomLeft.set(viewBounds.x - layerOffsetX, viewBounds.y + viewBounds.height - layerOffsetY, 0);
        // ROW1
        topLeft.set(viewBounds.x - layerOffsetX, viewBounds.y - layerOffsetY, 0);
        // ROW2
        bottomRight.set(viewBounds.x + viewBounds.width - layerOffsetX, viewBounds.y + viewBounds.height - layerOffsetY,
                0);

        // transforming screen coordinates to iso coordinates
        CoordinateCalculator coordinateCalculator = new CoordinateCalculator();

        GdxWorldIsoCoordinates topLeftGdxWorldIso = coordinateCalculator.world2iso(topLeft);
        GdxWorldIsoCoordinates bottomRightGdxWorldIso = coordinateCalculator.world2iso(bottomRight);
        GdxWorldIsoCoordinates bottomLeftGdxWorldIso = coordinateCalculator.world2iso(bottomLeft);
        GdxWorldIsoCoordinates topRightGdxWorldIso = coordinateCalculator.world2iso(topRight);

        int row1 = (int) (topLeftGdxWorldIso.y / tileWidth) - 2;
        int row2 = (int) (bottomRightGdxWorldIso.y / tileWidth) + 2;

        int col1 = (int) (bottomLeftGdxWorldIso.x / tileWidth) - 2;
        int col2 = (int) (topRightGdxWorldIso.x / tileWidth) + 2;

        for (int row = row2; row >= row1; row--)
        {
            for (int col = col1; col <= col2; col++)
            {
                float x = (col * halfTileWidth) + (row * halfTileWidth);
                float y = (row * halfTileHeight) - (col * halfTileHeight) - halfTileHeight;

                final TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null)
                    continue;
                final TiledMapTile tile = cell.getTile();

                if (tile != null)
                {
                    final boolean flipX = cell.getFlipHorizontally();
                    final boolean flipY = cell.getFlipVertically();
                    final int rotations = cell.getRotation();

                    TextureRegion region = tile.getTextureRegion();

                    float x1 = x + tile.getOffsetX() * unitScale + layerOffsetX;
                    float y1 = y + tile.getOffsetY() * unitScale + layerOffsetY;
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

                    if (flipX)
                    {
                        float temp = vertices[U1];
                        vertices[U1] = vertices[U3];
                        vertices[U3] = temp;
                        temp = vertices[U2];
                        vertices[U2] = vertices[U4];
                        vertices[U4] = temp;
                    }
                    if (flipY)
                    {
                        float temp = vertices[V1];
                        vertices[V1] = vertices[V3];
                        vertices[V3] = temp;
                        temp = vertices[V2];
                        vertices[V2] = vertices[V4];
                        vertices[V4] = temp;
                    }
                    if (rotations != 0)
                    {
                        switch (rotations)
                        {
                            case Cell.ROTATE_90:
                            {
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V2];
                                vertices[V2] = vertices[V3];
                                vertices[V3] = vertices[V4];
                                vertices[V4] = tempV;

                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U2];
                                vertices[U2] = vertices[U3];
                                vertices[U3] = vertices[U4];
                                vertices[U4] = tempU;
                                break;
                            }
                            case Cell.ROTATE_180:
                            {
                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U3];
                                vertices[U3] = tempU;
                                tempU = vertices[U2];
                                vertices[U2] = vertices[U4];
                                vertices[U4] = tempU;
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V3];
                                vertices[V3] = tempV;
                                tempV = vertices[V2];
                                vertices[V2] = vertices[V4];
                                vertices[V4] = tempV;
                                break;
                            }
                            case Cell.ROTATE_270:
                            {
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V4];
                                vertices[V4] = vertices[V3];
                                vertices[V3] = vertices[V2];
                                vertices[V2] = tempV;

                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U4];
                                vertices[U4] = vertices[U3];
                                vertices[U3] = vertices[U2];
                                vertices[U2] = tempU;
                                break;
                            }
                        }
                    }
                    batch.draw(region.getTexture(), vertices, 0, NUM_VERTICES);
                }
            }
        }
    }

    @Override
    public void renderObjects(MapLayer layer)
    {
        final Color batchColor = batch.getColor();
        final float color = Color.toFloatBits(batchColor.r, batchColor.g, batchColor.b,
                batchColor.a * layer.getOpacity());

        int mapHeight = (int) getKKTiledMap().getHeightInTiles();
        int mapTileHeight = (int) getKKTiledMap().getTileHeightInPixels();

        List<TiledMapTileMapObject> objects = getTiledMapTileMapObjectList(layer.getObjects());
        ByDistanceToTmxIsoOriginComparator comparator = new ByDistanceToTmxIsoOriginComparator(mapHeight,
                mapTileHeight);
        Collections.sort(objects, comparator);

        for (TiledMapTileMapObject tiledMapTileMapObject : objects)
        {
            TextureRegion region = tiledMapTileMapObject.getTextureRegion();

            float x1 = (float) (tiledMapTileMapObject.getX() - (region.getRegionWidth() / 2.0) * unitScale);
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

            if (tiledMapTileMapObject instanceof KKMapObjectIf)
            {
                ((KKMapObjectIf) tiledMapTileMapObject).draw(batch, vertices, 0, NUM_VERTICES);
            } else
            {
                batch.draw(region.getTexture(), vertices, 0, NUM_VERTICES);
            }

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
                    new GdxWorldOrthoCoordinates(o1.getX(), o1.getY(), 0));
            float o2Distance = calculateDistanceToTmxoIsoOrigin(tileMapHeightInTiles, tileHeightInPixels,
                    new GdxWorldOrthoCoordinates(o2.getX(), o2.getY(), 0));

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
                GdxWorldOrthoCoordinates world)
        {
            Vector3 tmxIso = coordinateCalculator.world2TmxIso(tileMapHeightInTiles, tileHeightInPixels, world);

            return (float) Math.sqrt(tmxIso.x * tmxIso.x + tmxIso.y * tmxIso.y);
        }
    }
}
