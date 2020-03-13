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
    private TextureRegion wheatGardenCell2;
    private TextureRegion wheatGardenCell3;

    private TextureRegion growingObjectTextureRegion = null;

    public GardenCellObject(TiledMapTile tile, int id, String objectType) {
        super(tile, id, objectType);

        // TODO: add cache for texture region
        Texture textureWheat1 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_WHEAT_GARDEN_1);
        wheatGardenCell1 = new TextureRegion(textureWheat1);
        Texture textureWheat2 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_WHEAT_GARDEN_2);
        wheatGardenCell2 = new TextureRegion(textureWheat2);
        Texture textureWheat3 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_WHEAT_GARDEN_3);
        wheatGardenCell3 = new TextureRegion(textureWheat3);
    }

    public void setWheatPhase1()
    {
        growingObjectTextureRegion = wheatGardenCell1;
    }

    public void setWheatPhase2()
    {
        growingObjectTextureRegion = wheatGardenCell2;
    }

    public void setWheatPhase3()
    {
        growingObjectTextureRegion = wheatGardenCell3;
    }

    @Override
    protected void drawTextures(Batch batch, float layerOpacity, float unitScale)
    {
        // draw a default garden cell
        super.drawTextures(batch, layerOpacity, unitScale);

        // draw the plant
        if (growingObjectTextureRegion != null)
        {
            drawTexture(batch, growingObjectTextureRegion, layerOpacity, unitScale);
        }
    }
}
