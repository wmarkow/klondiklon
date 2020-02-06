package com.github.wmarkow.klondiklon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wmarkow.klondiklon.map.KKMapObjectIf;

public class HomeLandLogic
{
    private static Logger LOGGER = LoggerFactory.getLogger(HomeLandLogic.class);

    public GrubbingType getGrubbingType(KKMapObjectIf mapObject)
    {
        if (ObjectTypes.FIR.equals(mapObject.getObjectType()))
        {
            return GrubbingType.CHOPPING;
        }
        if (ObjectTypes.COAL_LARGE.equals(mapObject.getObjectType()))
        {
            return GrubbingType.MINING;
        }
        if (ObjectTypes.COAL_MEDIUM.equals(mapObject.getObjectType()))
        {
            return GrubbingType.MINING;
        }
        if (ObjectTypes.COAL_SMALL.equals(mapObject.getObjectType()))
        {
            return GrubbingType.MINING;
        }

        return GrubbingType.NONE;
    }
}
