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

}
