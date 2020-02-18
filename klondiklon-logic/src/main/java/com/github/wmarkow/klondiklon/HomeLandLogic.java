package com.github.wmarkow.klondiklon;

import com.github.wmarkow.klondiklon.graphics.FontsManager;
import com.github.wmarkow.klondiklon.graphics.FontsRegistrar;
import com.github.wmarkow.klondiklon.graphics.TexturesManager;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
import com.github.wmarkow.klondiklon.objects.GrubbingProfit;
import com.github.wmarkow.klondiklon.objects.GrubbingType;
import com.github.wmarkow.klondiklon.objects.ObjectTypeDescriptor;
import com.github.wmarkow.klondiklon.objects.ObjectTypes;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptorsManager;
import com.github.wmarkow.klondiklon.objects.ObjectTypeDescriptorsManager;
import com.github.wmarkow.klondiklon.objects.StorageItemTypes;

public class HomeLandLogic
{
    public void initObjectTypeDescriptors(ObjectTypeDescriptorsManager manager)
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

    public void initStorageIetmDescriptors(StorageItemDescriptorsManager manager)
    {
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.BUSH_WOOD, TexturesRegistrar.STORAGE_ITEM_BUSH_WOOD));
        // manager.registerObjectTypeDescriptor(
        // new StorageItemDescriptor(StorageItemTypes.COAL,
        // TexturesRegistrar.STORAGE_ITEM_COAL));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.FIR_WOOD, TexturesRegistrar.STORAGE_ITEM_FIR_WOOD));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.GRASS, TexturesRegistrar.STORAGE_ITEM_GRASS));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.ICE, TexturesRegistrar.STORAGE_ITEM_ICE));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.STONE, TexturesRegistrar.STORAGE_ITEM_STONE));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.WOOD, TexturesRegistrar.STORAGE_ITEM_WOOD));
    }

    public void initFonts(FontsManager fontsManager)
    {
        FontsRegistrar registrar = new FontsRegistrar();
        registrar.register(fontsManager);
    }

    public void initTextures(TexturesManager texturesManager)
    {
        TexturesRegistrar registrar = new TexturesRegistrar();
        registrar.register(texturesManager);
    }

    private ObjectTypeDescriptor createFir()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.FIR, "Jodła");
        obj.setGrubbingType(GrubbingType.CHOPPING);
        obj.setEnergyToGrubb(30);
        obj.addGrubbingProfit(new GrubbingProfit(StorageItemTypes.FIR_WOOD, 8));

        return obj;
    }

    private ObjectTypeDescriptor createCoalLarge()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.COAL_LARGE, "Hałda węgla");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(40);
        obj.addGrubbingProfit(new GrubbingProfit(StorageItemTypes.COAL, 8));

        return obj;
    }

    private ObjectTypeDescriptor createCoalMedium()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.COAL_MEDIUM, "Hałda węgla");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(30);
        obj.addGrubbingProfit(new GrubbingProfit(StorageItemTypes.COAL, 6));

        return obj;
    }

    private ObjectTypeDescriptor createCoalSmall()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.COAL_SMALL, "Hałda węgla");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(20);
        obj.addGrubbingProfit(new GrubbingProfit(StorageItemTypes.COAL, 4));

        return obj;
    }

    private ObjectTypeDescriptor createIceColumn()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.ICE_COLUMN, "Kolumna lodowa");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(35);
        obj.addGrubbingProfit(new GrubbingProfit(StorageItemTypes.ICE, 10));

        return obj;
    }

    private ObjectTypeDescriptor createRockLarge()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.ROCK_LARGE, "Skała");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(55);
        obj.addGrubbingProfit(new GrubbingProfit(StorageItemTypes.STONE, 8));

        return obj;
    }

    private ObjectTypeDescriptor createRockMedium()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.ROCK_MEDIUM, "Skała");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(20);
        obj.addGrubbingProfit(new GrubbingProfit(StorageItemTypes.STONE, 6));

        return obj;
    }

    private ObjectTypeDescriptor createGrassSmall()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.GRASS_SMALL, "Trawa");
        obj.setGrubbingType(GrubbingType.DIGGING);
        obj.setEnergyToGrubb(4);
        obj.addGrubbingProfit(new GrubbingProfit(StorageItemTypes.GRASS, 3));

        return obj;
    }

    private ObjectTypeDescriptor createSnawyBuschMedium()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.SNOWY_BUSH_MEDIUM, "Ośnieżony krzak");
        obj.setGrubbingType(GrubbingType.DIGGING);
        obj.setEnergyToGrubb(20);
        obj.addGrubbingProfit(new GrubbingProfit(StorageItemTypes.BUSH_WOOD, 4));
        obj.addGrubbingProfit(new GrubbingProfit(StorageItemTypes.ICE, 1));

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
