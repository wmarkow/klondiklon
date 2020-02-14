package com.github.wmarkow.klondiklon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wmarkow.klondiklon.map.KKMapObjectIf;
import com.github.wmarkow.klondiklon.ui.tools.GrubbingType;

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
        if (ObjectTypes.ICE_COLUMN.equals(mapObject.getObjectType()))
        {
            return GrubbingType.MINING;
        }
        if (ObjectTypes.ROCK_LARGE.equals(mapObject.getObjectType()))
        {
            return GrubbingType.MINING;
        }
        if (ObjectTypes.ROCK_MEDIUM.equals(mapObject.getObjectType()))
        {
            return GrubbingType.MINING;
        }
        if (ObjectTypes.GRASS_SMALL.equals(mapObject.getObjectType()))
        {
            return GrubbingType.DIGGING;
        }
        if (ObjectTypes.SNOWY_BUSH_MEDIUM.equals(mapObject.getObjectType()))
        {
            return GrubbingType.DIGGING;
        }
        if (ObjectTypes.FRAGARIA.equals(mapObject.getObjectType()))
        {
            return GrubbingType.DIGGING;
        }
        if (ObjectTypes.RUBUS.equals(mapObject.getObjectType()))
        {
            return GrubbingType.DIGGING;
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
        if (ObjectTypes.ICE_COLUMN.equals(objectType))
        {
            return 55;
        }
        if (ObjectTypes.ROCK_LARGE.equals(objectType))
        {
            return 35;
        }
        if (ObjectTypes.ROCK_MEDIUM.equals(objectType))
        {
            return 20;
        }
        if (ObjectTypes.GRASS_SMALL.equals(objectType))
        {
            return 10;
        }
        if (ObjectTypes.SNOWY_BUSH_MEDIUM.equals(objectType))
        {
            return 20;
        }
        if (ObjectTypes.FRAGARIA.equals(objectType))
        {
            return 15;
        }
        if (ObjectTypes.RUBUS.equals(objectType))
        {
            return 25;
        }

        return 5;
    }

    public String getName(String objectType)
    {
        if (ObjectTypes.FIR.equals(objectType))
        {
            return "Jodła";
        }
        if (ObjectTypes.COAL_LARGE.equals(objectType))
        {
            return "Hałda węgla";
        }
        if (ObjectTypes.COAL_MEDIUM.equals(objectType))
        {
            return "Hałda węgla";
        }
        if (ObjectTypes.COAL_SMALL.equals(objectType))
        {
            return "Hałda węgla";
        }
        if (ObjectTypes.ICE_COLUMN.equals(objectType))
        {
            return "Kolumna lodowa";
        }
        if (ObjectTypes.ROCK_LARGE.equals(objectType))
        {
            return "Skała";
        }
        if (ObjectTypes.ROCK_MEDIUM.equals(objectType))
        {
            return "Skała";
        }
        if (ObjectTypes.GRASS_SMALL.equals(objectType))
        {
            return "Trawa";
        }
        if (ObjectTypes.SNOWY_BUSH_MEDIUM.equals(objectType))
        {
            return "Ośnieżony krzak";
        }
        if (ObjectTypes.FRAGARIA.equals(objectType))
        {
            return "Poziomka";
        }
        if (ObjectTypes.RUBUS.equals(objectType))
        {
            return "Jeżyna";
        }

        return null;
    }
}
