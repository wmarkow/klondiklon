package com.github.wmarkow.klondiklon.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;

public class Warehouse
{
    @JsonProperty("items")
    private Map<String, Integer> items = new HashMap<String, Integer>();

    public synchronized int getItemQuantity(StorageItemDescriptor descriptor)
    {
        String storageItemType = descriptor.getStorageItemType();

        if (items.containsKey(storageItemType))
        {
            return items.get(storageItemType);
        }

        return 0;
    }

    public synchronized List<WarehouseItemQuantity> getWarehouseItemQuantities()
    {
        List<WarehouseItemQuantity> result = new ArrayList<WarehouseItemQuantity>();
        for (String storageItemType : items.keySet())
        {
            int quantity = items.get(storageItemType);
            result.add(new WarehouseItemQuantity(storageItemType, quantity));
        }

        return result;
    }

    public synchronized void addItemQuantity(StorageItemDescriptor descriptor, int deltaQuantity)
    {
        if (deltaQuantity < 0)
        {
            throw new IllegalArgumentException("Quantity may not be negative");
        }

        String storageItemType = descriptor.getStorageItemType();

        if (items.containsKey(storageItemType))
        {
            int current = items.get(storageItemType);

            items.put(storageItemType, current + deltaQuantity);

            return;
        }

        items.put(storageItemType, deltaQuantity);
    }
}
