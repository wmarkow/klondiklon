package com.github.wmarkow.klondiklon.tiled;

import com.github.wmarkow.klondiklon.tiled.tmx.ObjectGroupElement;

public class TmxObjectGroupLayer extends TmxLayer
{
    private ObjectGroupElement objectGroupElement;

    TmxObjectGroupLayer(ObjectGroupElement objectGroupElement) {
        this.objectGroupElement = objectGroupElement;
    }

    @Override
    public String getName()
    {
        return objectGroupElement.getName();
    }

}
