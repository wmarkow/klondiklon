package com.github.wmarkow.klondiklon.warehouse;

public class WarehouseItemQuantity
{
    private String storageItemType;
    private int quantity;

    public WarehouseItemQuantity(String storageItemType, int quantity) {
        if (storageItemType == null)
        {
            throw new IllegalArgumentException("StorageItemType must not be null.");
        }
        this.storageItemType = storageItemType;
        this.quantity = quantity;
    }

    public String getStorageItemType()
    {
        return storageItemType;
    }

    public int getQuantity()
    {
        return quantity;
    }
}
