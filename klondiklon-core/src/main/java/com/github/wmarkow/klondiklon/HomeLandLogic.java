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

    public int energyToGrub(String objectType)
    {
        if (ObjectTypes.FIR.equals(objectType))
        {
            return 30;
        }
        if (ObjectTypes.COAL_LARGE.equals(objectType))
        {
            return 40;
        }
        if (ObjectTypes.COAL_MEDIUM.equals(objectType))
        {
            return 30;
        }
        if (ObjectTypes.COAL_SMALL.equals(objectType))
        {
            return 20;
        }

        return 5;
    }
}
