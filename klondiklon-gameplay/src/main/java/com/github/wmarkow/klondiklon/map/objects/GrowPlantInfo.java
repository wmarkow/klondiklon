package com.github.wmarkow.klondiklon.map.objects;

import java.util.ArrayList;
import java.util.List;

public class GrowPlantInfo
{
    // FIME: we grow by storage item type, however we grow a specific object
    private String storageItemType;
    private List<String> growPhasesTextures = new ArrayList<String>();
    private int growTimeInSeconds;

    public GrowPlantInfo(String storageItemType, int growTimeInSeconds) {
        this.storageItemType = storageItemType;
        this.growTimeInSeconds = growTimeInSeconds;
    }

    public void addgGrowPhaseTexture(String textureName)
    {
        growPhasesTextures.add(textureName);
    }

    public String getStorageItemType()
    {
        return storageItemType;
    }

    public List<String> getGrowPhasesTextures()
    {
        return growPhasesTextures;
    }

    public int getGrowTimeInSeconds()
    {
        return growTimeInSeconds;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((growPhasesTextures == null) ? 0 : growPhasesTextures.hashCode());
        result = prime * result + growTimeInSeconds;
        result = prime * result + ((storageItemType == null) ? 0 : storageItemType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GrowPlantInfo other = (GrowPlantInfo) obj;
        if (growPhasesTextures == null)
        {
            if (other.growPhasesTextures != null)
                return false;
        } else if (!growPhasesTextures.equals(other.growPhasesTextures))
            return false;
        if (growTimeInSeconds != other.growTimeInSeconds)
            return false;
        if (storageItemType == null)
        {
            if (other.storageItemType != null)
                return false;
        } else if (!storageItemType.equals(other.storageItemType))
            return false;
        return true;
    }
}
