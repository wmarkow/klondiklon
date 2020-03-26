package com.github.wmarkow.klondiklon.objects;

import java.util.ArrayList;
import java.util.List;

public class ObjectTypeDescriptorsManager
{
    private List<ObjectTypeDescriptor> descriptors = new ArrayList<ObjectTypeDescriptor>();

    public void registerObjectTypeDescriptor(ObjectTypeDescriptor descriptor)
    {
        descriptors.add(descriptor);
    }

    public ObjectTypeDescriptor getByObjectType(String objectType)
    {
        if (objectType == null)
        {
            return null;
        }

        for (ObjectTypeDescriptor descriptor : descriptors)
        {
            if (objectType.equals(descriptor.getType()))
            {
                return descriptor;
            }
        }

        return null;
    }
}
