package com.github.wmarkow.klondiklon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wmarkow.klondiklon.map.KKMapObjectIf;

public class HomeLandLogic
{
    private static Logger LOGGER = LoggerFactory.getLogger(HomeLandLogic.class);

    public GrubbingType getGrubbingType(KKMapObjectIf mapObject)
    {
        ObjectType objectType = ObjectType.valueOf(mapObject.getObjectType());
        if (ObjectType.FIR.equals(objectType))
        {
            return GrubbingType.CHOPPING;
        }

        return GrubbingType.NONE;
    }
}
