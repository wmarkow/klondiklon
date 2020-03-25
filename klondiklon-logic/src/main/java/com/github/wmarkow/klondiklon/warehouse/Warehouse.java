package com.github.wmarkow.klondiklon.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;

public class Warehouse
{
    private Map<String, Integer> warehouse = new HashMap<String, Integer>();

    public synchronized int getItemQuantity(StorageItemDescriptor descriptor)
    {
        String storageItemType = descriptor.getStorageItemType();

        if (warehouse.containsKey(storageItemType))
        {
            return warehouse.get(storageItemType);
        }

        return 0;
    }

    public synchronized List<WarehouseItemQuantity> getWarehouseItemQuantities()
    {
        List<WarehouseItemQuantity> result = new ArrayList<WarehouseItemQuantity>();
        for (String storageItemType : warehouse.keySet())
        {
            int quantity = warehouse.get(storageItemType);
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

        if (warehouse.containsKey(storageItemType))
        {
            int current = warehouse.get(storageItemType);

            warehouse.put(storageItemType, current + deltaQuantity);

            return;
        }

        warehouse.put(storageItemType, deltaQuantity);
    }
}
