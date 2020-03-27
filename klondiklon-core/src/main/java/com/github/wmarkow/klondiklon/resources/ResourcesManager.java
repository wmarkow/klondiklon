package com.github.wmarkow.klondiklon.resources;

import java.util.HashMap;
import java.util.Map;

public class ResourcesManager<T>
{
    private Map<String, T> resources = new HashMap<String, T>();

    public void registerResource(String name, T shader)
    {
        resources.put(name, shader);
    }

    public T getResource(String name)
    {
        return resources.get(name);
    }
}
