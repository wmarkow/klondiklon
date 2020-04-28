package com.github.wmarkow.klondiklon.tiled;

public abstract class TmxLayer
{
    private TmxTiledMap parentMap;

    TmxLayer(TmxTiledMap parentMap) {
        this.parentMap = parentMap;
    }

    public abstract String getName();

    protected TmxTiledMap getParentMap()
    {
        return this.parentMap;
    }
}
