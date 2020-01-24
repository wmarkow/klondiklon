package com.github.wmarkow.klondiklon;

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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class KKIsometricTiledMapRenderer extends IsometricTiledMapRenderer
{
    private Matrix4 isoTransform;
    private Matrix4 invIsotransform;

    public KKIsometricTiledMapRenderer(TiledMap map) {
        super(map, 1.0f);
        init();
    }

    private void init()
    {
        // create the isometric transform
        isoTransform = new Matrix4();
        isoTransform.idt();

        // isoTransform.translate(0, 32, 0);
        isoTransform.scale((float) (Math.sqrt(2.0) / 2.0), (float) (Math.sqrt(2.0) / 4.0), 1.0f);
        isoTransform.rotate(0.0f, 0.0f, 1.0f, -45);

        // ... and the inverse matrix
        invIsotransform = new Matrix4(isoTransform);
        invIsotransform.inv();
    }

    @Override
    public void renderObjects(MapLayer layer)
    {
        final Color batchColor = batch.getColor();
        final float color = Color.toFloatBits(batchColor.r, batchColor.g, batchColor.b,
                batchColor.a * layer.getOpacity());

        final float layerOffsetX = layer.getRenderOffsetX() * unitScale;
        // offset in tiled is y down, so we flip it
        final float layerOffsetY = -layer.getRenderOffsetY() * unitScale;

        // // setting up the screen points
        // // COL1
        // topRight.set(viewBounds.x + viewBounds.width - layerOffsetX, viewBounds.y -
        // layerOffsetY);
        // // COL2
        // bottomLeft.set(viewBounds.x - layerOffsetX, viewBounds.y + viewBounds.height
        // - layerOffsetY);
        // // ROW1
        // topLeft.set(viewBounds.x - layerOffsetX, viewBounds.y - layerOffsetY);
        // // ROW2
        // bottomRight.set(viewBounds.x + viewBounds.width - layerOffsetX, viewBounds.y
        // + viewBounds.height - layerOffsetY);

        for (MapObject object : layer.getObjects())
        {
            if (object == null)
            {
                continue;
            }

            if (!(object instanceof TiledMapTileMapObject))
            {
                continue;
            }

            TiledMapTileMapObject tiledMapTileMapObject = (TiledMapTileMapObject) object;

            TextureRegion region = tiledMapTileMapObject.getTextureRegion();

            Vector3 res = translateScreenToIso(new Vector2(tiledMapTileMapObject.getX(), tiledMapTileMapObject.getY()));

            // float x1 = res.x * unitScale;
            // float y1 = res.y * unitScale;
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

    private Vector3 translateScreenToIso(Vector2 vec)
    {
        Vector3 screenPos = new Vector3();

        screenPos.set(vec.x, vec.y, 0);
        screenPos.mul(invIsotransform);

        return screenPos;
    }
}
