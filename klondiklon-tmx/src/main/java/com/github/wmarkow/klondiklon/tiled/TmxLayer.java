package com.github.wmarkow.klondiklon.tiled;

public abstract class TmxLayer
{
    private String name;

    public TmxLayer(String name) {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
