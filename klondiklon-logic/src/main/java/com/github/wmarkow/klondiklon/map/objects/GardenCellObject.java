package com.github.wmarkow.klondiklon.map.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;

public class GardenCellObject extends KKMapObject
{
    private TextureRegion wheatPhase1;
    private TextureRegion wheatPhase2;
    private TextureRegion wheatPhase3;
    private TextureRegion wheatPhase4;

    private TextureRegion growingObjectTextureRegion = null;

    public GardenCellObject(TiledMapTile tile, int id, String objectType) {
        super(tile, id, objectType);

        // TODO: add cache for texture region
        Texture textureWheat1 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_WHEAT_GARDEN_1);
        wheatPhase1 = new TextureRegion(textureWheat1);
        Texture textureWheat2 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_WHEAT_GARDEN_2);
        wheatPhase2 = new TextureRegion(textureWheat2);
        Texture textureWheat3 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_WHEAT_GARDEN_3);
        wheatPhase3 = new TextureRegion(textureWheat3);
        Texture textureWheat4 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_WHEAT_GARDEN_4);
        wheatPhase4 = new TextureRegion(textureWheat4);
    }

    public void setWheatPhase1()
    {
        growingObjectTextureRegion = wheatPhase1;
    }

    public void setWheatPhase2()
    {
        growingObjectTextureRegion = wheatPhase2;
    }

    public void setWheatPhase3()
    {
        growingObjectTextureRegion = wheatPhase3;
    }

    public void setWheatPhase4()
    {
        growingObjectTextureRegion = wheatPhase4;
    }

    public boolean isReadyForSickle()
    {
        if (growingObjectTextureRegion == wheatPhase4)
        {
            return true;
        }

        return false;
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
