package com.github.wmarkow.klondiklon;

import com.github.wmarkow.klondiklon.home.objects.ObjectTypes;
import com.github.wmarkow.klondiklon.objects.ObjectTypeDescriptor;
import com.github.wmarkow.klondiklon.objects.ObjectTypesManager;
import com.github.wmarkow.klondiklon.ui.tools.GrubbingType;

public class HomeLandLogic
{
    public void init(ObjectTypesManager manager)
    {
        manager.registerObjectTypeDescriptor(createFir());
        manager.registerObjectTypeDescriptor(createCoalLarge());
        manager.registerObjectTypeDescriptor(createCoalMedium());
        manager.registerObjectTypeDescriptor(createCoalSmall());
        manager.registerObjectTypeDescriptor(createIceColumn());
        manager.registerObjectTypeDescriptor(createRockLarge());
        manager.registerObjectTypeDescriptor(createRockMedium());
        manager.registerObjectTypeDescriptor(createGrassSmall());
        manager.registerObjectTypeDescriptor(createSnawyBuschMedium());
        manager.registerObjectTypeDescriptor(createFragaria());
        manager.registerObjectTypeDescriptor(createRubus());
    }

    private ObjectTypeDescriptor createFir()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.FIR, "Jodła");
        obj.setGrubbingType(GrubbingType.CHOPPING);
        obj.setEnergyToGrubb(30);

        return obj;
    }

    private ObjectTypeDescriptor createCoalLarge()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.COAL_LARGE, "Hałda węgla");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(40);

        return obj;
    }

    private ObjectTypeDescriptor createCoalMedium()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.COAL_MEDIUM, "Hałda węgla");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(30);

        return obj;
    }

    private ObjectTypeDescriptor createCoalSmall()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.COAL_SMALL, "Hałda węgla");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(20);

        return obj;
    }

    private ObjectTypeDescriptor createIceColumn()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.ICE_COLUMN, "Kolumna lodowa");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(35);

        return obj;
    }

    private ObjectTypeDescriptor createRockLarge()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.ROCK_LARGE, "Skała");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(55);

        return obj;
    }

    private ObjectTypeDescriptor createRockMedium()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.ROCK_MEDIUM, "Skała");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(20);

        return obj;
    }

    private ObjectTypeDescriptor createGrassSmall()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.GRASS_SMALL, "Trawa");
        obj.setGrubbingType(GrubbingType.DIGGING);
        obj.setEnergyToGrubb(4);

        return obj;
    }

    private ObjectTypeDescriptor createSnawyBuschMedium()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.SNOWY_BUSH_MEDIUM, "Ośnieżony krzak");
        obj.setGrubbingType(GrubbingType.DIGGING);
        obj.setEnergyToGrubb(20);

        return obj;
    }

    private ObjectTypeDescriptor createFragaria()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.FRAGARIA, "Poziomka");
        obj.setGrubbingType(GrubbingType.DIGGING);
        obj.setEnergyToGrubb(10);

        return obj;
    }

    private ObjectTypeDescriptor createRubus()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.RUBUS, "Jeżyna");
        obj.setGrubbingType(GrubbingType.DIGGING);
        obj.setEnergyToGrubb(10);

        return obj;
    }
}
