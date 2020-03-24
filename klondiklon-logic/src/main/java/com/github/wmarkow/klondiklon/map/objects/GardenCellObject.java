package com.github.wmarkow.klondiklon.map.objects;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
import com.github.wmarkow.klondiklon.objects.GrubbingProfit;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;
import com.github.wmarkow.klondiklon.objects.StorageItemTypes;

public class GardenCellObject extends KKMapObject
{
    private TextureRegion wheatPhase1;
    private TextureRegion wheatPhase2;
    private TextureRegion wheatPhase3;
    private TextureRegion wheatPhase4;

    private TextureRegion beanPhase1;
    private TextureRegion beanPhase2;
    private TextureRegion beanPhase3;

    private TextureRegion grassPhase1;
    private TextureRegion grassPhase2;
    private TextureRegion grassPhase3;

    private TextureRegion cornPhase1;
    private TextureRegion cornPhase2;
    private TextureRegion cornPhase3;

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

        Texture textureBean1 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_BEAN_GARDEN_1);
        beanPhase1 = new TextureRegion(textureBean1);
        Texture textureBean2 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_BEAN_GARDEN_2);
        beanPhase2 = new TextureRegion(textureBean2);
        Texture textureBean3 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_BEAN_GARDEN_3);
        beanPhase3 = new TextureRegion(textureBean3);

        Texture textureGrass1 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_GRASS_GARDEN_1);
        grassPhase1 = new TextureRegion(textureGrass1);
        Texture textureGrass2 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_GRASS_GARDEN_2);
        grassPhase2 = new TextureRegion(textureGrass2);
        Texture textureGrass3 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_GRASS_GARDEN_3);
        grassPhase3 = new TextureRegion(textureGrass3);

        Texture textureCorn1 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_CORN_GARDEN_1);
        cornPhase1 = new TextureRegion(textureCorn1);
        Texture textureCorn2 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_CORN_GARDEN_2);
        cornPhase2 = new TextureRegion(textureCorn2);
        Texture textureCorn3 = ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.OBJECT_CORN_GARDEN_3);
        cornPhase3 = new TextureRegion(textureCorn3);
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

    public void setBeanPhase1()
    {
        growingObjectTextureRegion = beanPhase1;
    }

    public void setBeanPhase2()
    {
        growingObjectTextureRegion = beanPhase2;
    }

    public void setBeanPhase3()
    {
        growingObjectTextureRegion = beanPhase3;
    }

    public void setGrassPhase1()
    {
        growingObjectTextureRegion = grassPhase1;
    }

    public void setGrassPhase2()
    {
        growingObjectTextureRegion = grassPhase2;
    }

    public void setGrassPhase3()
    {
        growingObjectTextureRegion = grassPhase3;
    }

    public void setCornPhase1()
    {
        growingObjectTextureRegion = cornPhase1;
    }

    public void setCornPhase2()
    {
        growingObjectTextureRegion = cornPhase2;
    }

    public void setCornPhase3()
    {
        growingObjectTextureRegion = cornPhase3;
    }

    public boolean isReadyForSickle()
    {
        if (growingObjectTextureRegion == wheatPhase4)
        {
            return true;
        }

        if (growingObjectTextureRegion == beanPhase3)
        {
            return true;
        }

        if (growingObjectTextureRegion == grassPhase3)
        {
            return true;
        }

        if (growingObjectTextureRegion == cornPhase3)
        {
            return true;
        }

        return false;
    }

    public boolean isReadyForSeed()
    {
        if (growingObjectTextureRegion == null)
        {
            return true;
        }

        return false;
    }

    public Set<GrubbingProfit> sickleIt()
    {
        if (!isReadyForSickle())
        {
            return new HashSet<GrubbingProfit>();
        }

        StorageItemDescriptor wheatItemDescriptor = null;

        if (growingObjectTextureRegion == wheatPhase4)
        {
            wheatItemDescriptor = Klondiklon.storageItemDescriptorsManager.getByType(StorageItemTypes.WHEAT);
        } else if (growingObjectTextureRegion == beanPhase3)
        {
            wheatItemDescriptor = Klondiklon.storageItemDescriptorsManager.getByType(StorageItemTypes.BEAN);
        } else if (growingObjectTextureRegion == grassPhase3)
        {
            wheatItemDescriptor = Klondiklon.storageItemDescriptorsManager.getByType(StorageItemTypes.GRASS);
        } else if (growingObjectTextureRegion == cornPhase3)
        {
            wheatItemDescriptor = Klondiklon.storageItemDescriptorsManager.getByType(StorageItemTypes.CORN);
        }

        growingObjectTextureRegion = null;

        if (wheatItemDescriptor == null)
        {
            return new HashSet<GrubbingProfit>();
        }

        Set<GrubbingProfit> result = new HashSet<GrubbingProfit>();
        result.add(new GrubbingProfit(wheatItemDescriptor, 1));

        return result;
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
