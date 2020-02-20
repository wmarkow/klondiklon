package com.github.wmarkow.klondiklon.warehouse;

import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;

public class WarehouseItemQuantity
{
    private StorageItemDescriptor storageItemDescriptor;
    private int quantity;

    public WarehouseItemQuantity(StorageItemDescriptor storageItemDescriptor, int quantity) {
        if (storageItemDescriptor == null)
        {
            throw new IllegalArgumentException("StorageItemDescriptor must not be null.");
        }
        this.storageItemDescriptor = storageItemDescriptor;
        this.quantity = quantity;
    }

    public StorageItemDescriptor getStorageItemDescriptor()
    {
        return storageItemDescriptor;
    }

    public int getQuantity()
    {
        return quantity;
    }
}
