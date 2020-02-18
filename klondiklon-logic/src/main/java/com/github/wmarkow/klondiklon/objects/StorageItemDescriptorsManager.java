package com.github.wmarkow.klondiklon.objects;

import java.util.ArrayList;
import java.util.List;

public class StorageItemDescriptorsManager
{
    private List<StorageItemDescriptor> descriptors = new ArrayList<StorageItemDescriptor>();

    public void registerObjectTypeDescriptor(StorageItemDescriptor descriptor)
    {
        descriptors.add(descriptor);
    }

    public StorageItemDescriptor getByName(String name)
    {
        if (name == null)
        {
            return null;
        }

        for (StorageItemDescriptor descriptor : descriptors)
        {
            if (name.equals(descriptor.getStorageItemType()))
            {
                return descriptor;
            }
        }

        return null;
    }
}
