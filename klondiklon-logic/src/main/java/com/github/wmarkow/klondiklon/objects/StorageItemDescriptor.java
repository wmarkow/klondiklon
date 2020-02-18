package com.github.wmarkow.klondiklon.objects;

public class StorageItemDescriptor
{
    private String storageItemType;
    private String textureName;

    public StorageItemDescriptor(String storageItemType, String textureName) {
        this.storageItemType = storageItemType;
        this.textureName = textureName;
    }

    public String getStorageItemType()
    {
        return storageItemType;
    }

    public String getTextureName()
    {
        return textureName;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((storageItemType == null) ? 0 : storageItemType.hashCode());
        result = prime * result + ((textureName == null) ? 0 : textureName.hashCode());
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
        StorageItemDescriptor other = (StorageItemDescriptor) obj;
        if (storageItemType == null)
        {
            if (other.storageItemType != null)
                return false;
        } else if (!storageItemType.equals(other.storageItemType))
            return false;
        if (textureName == null)
        {
            if (other.textureName != null)
                return false;
        } else if (!textureName.equals(other.textureName))
            return false;
        return true;
    }

}
