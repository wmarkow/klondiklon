package com.github.wmarkow.klondiklon.map.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;

public class GardenCellObject extends KKMapObject
{
    private Texture wheatGardenCell1;

    public GardenCellObject(TiledMapTile tile, int id, String objectType) {
        super(tile, id, objectType);

        wheatGardenCell1 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_WHEAT_GARDEN_1);
    }

    @Override
    protected void drawTextures(Batch batch, float[] spriteVertices, int offset, int count)
    {
        // draw a default garden cell
        super.drawTextures(batch, spriteVertices, offset, count);

        // draw the plant
        batch.draw(wheatGardenCell1, spriteVertices, offset, count);
    }
}
