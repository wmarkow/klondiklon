package com.github.wmarkow.klondiklon.warehouse;

import java.util.HashMap;
import java.util.Map;

import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;

public class Warehouse
{
    private Map<StorageItemDescriptor, Integer> warehouse = new HashMap<StorageItemDescriptor, Integer>();

    public synchronized int getItemQuantity(StorageItemDescriptor descriptor)
    {
        if (warehouse.containsKey(descriptor))
        {
            return warehouse.get(descriptor);
        }

        return 0;
    }

    public synchronized void addItemQuantity(StorageItemDescriptor descriptor, int deltaQuantity)
    {
        if (deltaQuantity < 0)
        {
            throw new IllegalArgumentException("Quantity may not be negative");
        }

        if (warehouse.containsKey(descriptor))
        {
            int current = warehouse.get(descriptor);

            warehouse.put(descriptor, current + deltaQuantity);

            return;
        }

        warehouse.put(descriptor, deltaQuantity);
    }
}
