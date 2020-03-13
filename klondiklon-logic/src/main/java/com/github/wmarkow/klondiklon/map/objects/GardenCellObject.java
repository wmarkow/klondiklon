package com.github.wmarkow.klondiklon.map.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;

public class GardenCellObject extends KKMapObject
{
    private TextureRegion wheatGardenCell1;

    public GardenCellObject(TiledMapTile tile, int id, String objectType) {
        super(tile, id, objectType);

        Texture textureWheat1 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_WHEAT_GARDEN_1);

        wheatGardenCell1 = new TextureRegion(textureWheat1);
    }

    @Override
    protected void drawTextures(Batch batch, float layerOpacity, float unitScale)
    {
        // draw a default garden cell
        super.drawTextures(batch, layerOpacity, unitScale);

        // draw the plant
        drawTexture(batch, wheatGardenCell1, layerOpacity, unitScale);
    }
}
