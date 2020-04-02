package com.github.wmarkow.klondiklon.map.objects;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.github.wmarkow.klondiklon.GameplayService;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.objects.GrubbingProfit;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;

public class GardenCellObject extends KKMapObject
{
    private TextureRegion growingObjectTextureRegion = null;

    private GrowPlantInfo growPlantInfo = null;
    private int growPhase = -1;

    public GardenCellObject(TiledMapTile tile, int id, String objectType) {
        super(tile, id, objectType);
    }

    public void setGrowPlantPhase(GrowPlantInfo growPlantInfo, int growPhaseIndex)
    {
        if (growPlantInfo == null)
        {
            throw new IllegalArgumentException("GrowPlantInfo must not be null");
        }
        if (growPhaseIndex < 0)
        {
            throw new IllegalArgumentException("GrowPlantInfo must not be negative");
        }
        if (growPhaseIndex >= growPlantInfo.getGrowPhasesTextures().size())
        {
            throw new IllegalArgumentException("GrowPhaseIndex to hight");
        }

        boolean changeTexture = false;
        if (this.growPlantInfo == null)
        {
            changeTexture = true;
        } else if (this.growPhase != growPhaseIndex)
        {
            changeTexture = true;
        } else if (this.growPlantInfo != growPlantInfo)
        {
            changeTexture = true;
        }

        this.growPlantInfo = growPlantInfo;
        this.growPhase = growPhaseIndex;

        if (changeTexture)
        {
            String textureName = growPlantInfo.getGrowPhasesTextures().get(growPhaseIndex);
            Texture texture = ServiceRegistry.getInstance().getTexturesManager().getTexture(textureName);
            growingObjectTextureRegion = new TextureRegion(texture);
        }
    }

    public boolean isReadyForSickle()
    {
        if (growPlantInfo == null)
        {
            return false;
        }
        if (growPlantInfo.getGrowPhasesTextures().size() - 1 == growPhase)
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

        StorageItemDescriptor itemDescriptor = GameplayService.getInstance().getStorageItemDescriptorsManager()
                .getByType(growPlantInfo.getStorageItemType());

        growingObjectTextureRegion = null;
        growPlantInfo = null;
        growPhase = -1;

        if (itemDescriptor == null)
        {
            return new HashSet<GrubbingProfit>();
        }

        Set<GrubbingProfit> result = new HashSet<GrubbingProfit>();
        result.add(new GrubbingProfit(itemDescriptor, 1));

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
