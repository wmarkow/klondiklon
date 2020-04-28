package com.github.wmarkow.klondiklon.tiled;

import java.util.ArrayList;
import java.util.List;

import com.github.wmarkow.klondiklon.tiled.tmx.ObjectElement;
import com.github.wmarkow.klondiklon.tiled.tmx.ObjectGroupElement;

public class TmxObjectGroupLayer extends TmxLayer
{
    private ObjectGroupElement objectGroupElement;

    TmxObjectGroupLayer(ObjectGroupElement objectGroupElement, TmxTiledMap parentMap) {
        super(parentMap);

        this.objectGroupElement = objectGroupElement;
    }

    @Override
    public String getName()
    {
        return objectGroupElement.getName();
    }

    public TmxObject[] getObjects()
    {
        List<TmxObject> result = new ArrayList<TmxObject>();

        for (ObjectElement objectElement : objectGroupElement.getObjects())
        {
            result.add(new TmxObject(objectElement, getParentMap()));
        }

        return result.toArray(new TmxObject[]
        {});
    }
}
