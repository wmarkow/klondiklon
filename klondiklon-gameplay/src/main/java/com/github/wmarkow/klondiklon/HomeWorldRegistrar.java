package com.github.wmarkow.klondiklon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.github.wmarkow.klondiklon.objects.GrubbingProfit;
import com.github.wmarkow.klondiklon.objects.GrubbingType;
import com.github.wmarkow.klondiklon.objects.ObjectTypeDescriptor;
import com.github.wmarkow.klondiklon.objects.ObjectTypeDescriptorsManager;
import com.github.wmarkow.klondiklon.objects.ObjectTypes;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptorsManager;
import com.github.wmarkow.klondiklon.objects.StorageItemTypes;
import com.github.wmarkow.klondiklon.resources.graphics.FontsManager;
import com.github.wmarkow.klondiklon.resources.graphics.TexturesManager;
import com.github.wmarkow.klondiklon.resources.music.MusicManager;
import com.github.wmarkow.klondiklon.resources.sound.SoundManager;
import com.github.wmarkow.klondiklon.worlds.WorldRegistrar;

public class HomeWorldRegistrar implements WorldRegistrar
{
    private static Logger LOGGER = LoggerFactory.getLogger(HomeWorldRegistrar.class);

    public final static String STORAGE_ITEM_ICE = "STORAGE_ITEM_ICE";
    public final static String STORAGE_ITEM_STONE = "STORAGE_ITEM_STONE";
    public final static String STORAGE_ITEM_WOOD = "STORAGE_ITEM_WOOD";
    public final static String STORAGE_ITEM_FIR_WOOD = "STORAGE_ITEM_FIR_WOOD";
    public final static String STORAGE_ITEM_BUSH_WOOD = "STORAGE_ITEM_BUSH_WOOD";
    public final static String STORAGE_ITEM_GRASS = "STORAGE_ITEM_GRASS";
    public final static String STORAGE_ITEM_FRAGARIA = "STORAGE_ITEM_FRAGARIA";
    public final static String STORAGE_ITEM_RUBUS = "STORAGE_ITEM_RUBUS";
    public final static String STORAGE_ITEM_COAL = "STORAGE_ITEM_COAL";
    public final static String STORAGE_ITEM_WHEAT = "STORAGE_ITEM_WHEAT";
    public final static String STORAGE_ITEM_BEAN = "STORAGE_ITEM_BEAN";
    public final static String STORAGE_ITEM_CORN = "STORAGE_ITEM_CORN";
    public final static String STORAGE_ITEM_STRAWBERRY = "STORAGE_ITEM_STRAWBERRY";

    public final static String OBJECT_WHEAT_GARDEN_1 = "OBJECT_WHEAT_GARDEN_1";
    public final static String OBJECT_WHEAT_GARDEN_2 = "OBJECT_WHEAT_GARDEN_2";
    public final static String OBJECT_WHEAT_GARDEN_3 = "OBJECT_WHEAT_GARDEN_3";
    public final static String OBJECT_WHEAT_GARDEN_4 = "OBJECT_WHEAT_GARDEN_4";
    public final static String OBJECT_BEAN_GARDEN_1 = "OBJECT_BEAN_GARDEN_1";
    public final static String OBJECT_BEAN_GARDEN_2 = "OBJECT_BEAN_GARDEN_2";
    public final static String OBJECT_BEAN_GARDEN_3 = "OBJECT_BEAN_GARDEN_3";
    public final static String OBJECT_GRASS_GARDEN_1 = "OBJECT_GRASS_GARDEN_1";
    public final static String OBJECT_GRASS_GARDEN_2 = "OBJECT_GRASS_GARDEN_2";
    public final static String OBJECT_GRASS_GARDEN_3 = "OBJECT_GRASS_GARDEN_3";
    public final static String OBJECT_CORN_GARDEN_1 = "OBJECT_CORN_GARDEN_1";
    public final static String OBJECT_CORN_GARDEN_2 = "OBJECT_CORN_GARDEN_2";
    public final static String OBJECT_CORN_GARDEN_3 = "OBJECT_CORN_GARDEN_3";
    public final static String OBJECT_STRAWBERRY_GARDEN_1 = "OBJECT_STRAWBERRY_GARDEN_1";
    public final static String OBJECT_STRAWBERRY_GARDEN_2 = "OBJECT_STRAWBERRY_GARDEN_2";
    public final static String OBJECT_STRAWBERRY_GARDEN_3 = "OBJECT_STRAWBERRY_GARDEN_3";

    @Override
    public void registerFonts(FontsManager fontsManager)
    {
        // nothing to register
    }

    @Override
    public void registerTextures(TexturesManager texturesManager)
    {
        texturesManager.registerTexture(STORAGE_ITEM_ICE,
                new Texture(Gdx.files.local("worlds/home/warehouse/items/ice.png")));
        texturesManager.registerTexture(STORAGE_ITEM_STONE,
                new Texture(Gdx.files.local("worlds/home/warehouse/items/stone.png")));
        texturesManager.registerTexture(STORAGE_ITEM_WOOD,
                new Texture(Gdx.files.local("worlds/home/warehouse/items/wood.png")));
        texturesManager.registerTexture(STORAGE_ITEM_FIR_WOOD,
                new Texture(Gdx.files.local("worlds/home/warehouse/items/wood_fir.png")));
        texturesManager.registerTexture(STORAGE_ITEM_BUSH_WOOD,
                new Texture(Gdx.files.local("worlds/home/warehouse/items/wood_bush.png")));
        texturesManager.registerTexture(STORAGE_ITEM_GRASS,
                new Texture(Gdx.files.local("worlds/home/warehouse/items/grass.png")));
        texturesManager.registerTexture(STORAGE_ITEM_FRAGARIA,
                new Texture(Gdx.files.local("worlds/home/warehouse/items/fragaria.png")));
        texturesManager.registerTexture(STORAGE_ITEM_RUBUS,
                new Texture(Gdx.files.local("worlds/home/warehouse/items/rubus.png")));
        texturesManager.registerTexture(STORAGE_ITEM_COAL,
                new Texture(Gdx.files.local("worlds/home/warehouse/items/coal.png")));
        texturesManager.registerTexture(STORAGE_ITEM_WHEAT,
                new Texture(Gdx.files.local("worlds/home/warehouse/items/wheat.png")));
        texturesManager.registerTexture(STORAGE_ITEM_BEAN,
                new Texture(Gdx.files.local("worlds/home/warehouse/items/bean.png")));
        texturesManager.registerTexture(STORAGE_ITEM_CORN,
                new Texture(Gdx.files.local("worlds/home/warehouse/items/corn.png")));
        texturesManager.registerTexture(STORAGE_ITEM_STRAWBERRY,
                new Texture(Gdx.files.local("worlds/home/warehouse/items/strawberry.png")));

        texturesManager.registerTexture(OBJECT_WHEAT_GARDEN_1,
                new Texture(Gdx.files.local("worlds/home/object_wheat_garden_1.png")));
        texturesManager.registerTexture(OBJECT_WHEAT_GARDEN_2,
                new Texture(Gdx.files.local("worlds/home/object_wheat_garden_2.png")));
        texturesManager.registerTexture(OBJECT_WHEAT_GARDEN_3,
                new Texture(Gdx.files.local("worlds/home/object_wheat_garden_3.png")));
        texturesManager.registerTexture(OBJECT_WHEAT_GARDEN_4,
                new Texture(Gdx.files.local("worlds/home/object_wheat_garden_4.png")));
        texturesManager.registerTexture(OBJECT_BEAN_GARDEN_1,
                new Texture(Gdx.files.local("worlds/home/object_bean_garden_1.png")));
        texturesManager.registerTexture(OBJECT_BEAN_GARDEN_2,
                new Texture(Gdx.files.local("worlds/home/object_bean_garden_2.png")));
        texturesManager.registerTexture(OBJECT_BEAN_GARDEN_3,
                new Texture(Gdx.files.local("worlds/home/object_bean_garden_3.png")));

        texturesManager.registerTexture(OBJECT_GRASS_GARDEN_1,
                new Texture(Gdx.files.local("worlds/home/object_grass_garden_1.png")));
        texturesManager.registerTexture(OBJECT_GRASS_GARDEN_2,
                new Texture(Gdx.files.local("worlds/home/object_grass_garden_2.png")));
        texturesManager.registerTexture(OBJECT_GRASS_GARDEN_3,
                new Texture(Gdx.files.local("worlds/home/object_grass_garden_3.png")));

        texturesManager.registerTexture(OBJECT_CORN_GARDEN_1,
                new Texture(Gdx.files.local("worlds/home/object_corn_garden_1.png")));
        texturesManager.registerTexture(OBJECT_CORN_GARDEN_2,
                new Texture(Gdx.files.local("worlds/home/object_corn_garden_2.png")));
        texturesManager.registerTexture(OBJECT_CORN_GARDEN_3,
                new Texture(Gdx.files.local("worlds/home/object_corn_garden_3.png")));

        texturesManager.registerTexture(OBJECT_STRAWBERRY_GARDEN_1,
                new Texture(Gdx.files.local("worlds/home/object_strawberry_garden_1.png")));
        texturesManager.registerTexture(OBJECT_STRAWBERRY_GARDEN_2,
                new Texture(Gdx.files.local("worlds/home/object_strawberry_garden_2.png")));
        texturesManager.registerTexture(OBJECT_STRAWBERRY_GARDEN_3,
                new Texture(Gdx.files.local("worlds/home/object_strawberry_garden_3.png")));
    }

    @Override
    public void registerMusics(MusicManager musicManager)
    {
        // nothing to register
    }

    @Override
    public void registerSounds(SoundManager soundManager)
    {
        // nothing to register
    }

    @Override
    public void registerStorageItemDescriptors(StorageItemDescriptorsManager manager)
    {
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.BUSH_WOOD, HomeWorldRegistrar.STORAGE_ITEM_BUSH_WOOD));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.COAL, HomeWorldRegistrar.STORAGE_ITEM_COAL));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.FIR_WOOD, HomeWorldRegistrar.STORAGE_ITEM_FIR_WOOD));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.GRASS, HomeWorldRegistrar.STORAGE_ITEM_GRASS));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.ICE, HomeWorldRegistrar.STORAGE_ITEM_ICE));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.STONE, HomeWorldRegistrar.STORAGE_ITEM_STONE));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.WOOD, HomeWorldRegistrar.STORAGE_ITEM_WOOD));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.FRAGARIA, HomeWorldRegistrar.STORAGE_ITEM_FRAGARIA));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.RUBUS, HomeWorldRegistrar.STORAGE_ITEM_RUBUS));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.WHEAT, HomeWorldRegistrar.STORAGE_ITEM_WHEAT));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.BEAN, HomeWorldRegistrar.STORAGE_ITEM_BEAN));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.CORN, HomeWorldRegistrar.STORAGE_ITEM_CORN));
        manager.registerObjectTypeDescriptor(
                new StorageItemDescriptor(StorageItemTypes.STRAWBERRY, HomeWorldRegistrar.STORAGE_ITEM_STRAWBERRY));
    }

    @Override
    public void registerObjectTypeDescriptors(ObjectTypeDescriptorsManager manager)
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

        manager.registerObjectTypeDescriptor(createBarn());
    }

    @Override
    public void copyResourcesToLocal()
    {
        GameplayService.getInstance().resourcesToLocalCopier.copyResourcesToLocal();
        FileHandle fileHandle = Gdx.files.internal("worlds/home");
        fileHandle.list();
    }

    private ObjectTypeDescriptor createFir()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.FIR, "Jodła");
        obj.setGrubbingType(GrubbingType.CHOPPING);
        obj.setEnergyToGrubb(30);
        obj.addGrubbingProfit(new GrubbingProfit(
                GameplayService.getInstance().getStorageItemDescriptorsManager().getByType(StorageItemTypes.FIR_WOOD),
                8));

        return obj;
    }

    private ObjectTypeDescriptor createCoalLarge()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.COAL_LARGE, "Hałda węgla");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(40);
        obj.addGrubbingProfit(new GrubbingProfit(
                GameplayService.getInstance().getStorageItemDescriptorsManager().getByType(StorageItemTypes.COAL), 8));

        return obj;
    }

    private ObjectTypeDescriptor createCoalMedium()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.COAL_MEDIUM, "Hałda węgla");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(30);
        obj.addGrubbingProfit(new GrubbingProfit(
                GameplayService.getInstance().getStorageItemDescriptorsManager().getByType(StorageItemTypes.COAL), 6));

        return obj;
    }

    private ObjectTypeDescriptor createCoalSmall()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.COAL_SMALL, "Hałda węgla");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(20);
        obj.addGrubbingProfit(new GrubbingProfit(
                GameplayService.getInstance().getStorageItemDescriptorsManager().getByType(StorageItemTypes.COAL), 4));

        return obj;
    }

    private ObjectTypeDescriptor createIceColumn()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.ICE_COLUMN, "Kolumna lodowa");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(35);
        obj.addGrubbingProfit(new GrubbingProfit(
                GameplayService.getInstance().getStorageItemDescriptorsManager().getByType(StorageItemTypes.ICE), 10));

        return obj;
    }

    private ObjectTypeDescriptor createRockLarge()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.ROCK_LARGE, "Skała");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(55);
        obj.addGrubbingProfit(new GrubbingProfit(
                GameplayService.getInstance().getStorageItemDescriptorsManager().getByType(StorageItemTypes.STONE), 8));

        return obj;
    }

    private ObjectTypeDescriptor createRockMedium()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.ROCK_MEDIUM, "Skała");
        obj.setGrubbingType(GrubbingType.MINING);
        obj.setEnergyToGrubb(20);
        obj.addGrubbingProfit(new GrubbingProfit(
                GameplayService.getInstance().getStorageItemDescriptorsManager().getByType(StorageItemTypes.STONE), 6));

        return obj;
    }

    private ObjectTypeDescriptor createGrassSmall()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.GRASS_SMALL, "Trawa");
        obj.setGrubbingType(GrubbingType.DIGGING);
        obj.setEnergyToGrubb(4);
        obj.addGrubbingProfit(new GrubbingProfit(
                GameplayService.getInstance().getStorageItemDescriptorsManager().getByType(StorageItemTypes.GRASS), 3));

        return obj;
    }

    private ObjectTypeDescriptor createSnawyBuschMedium()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.SNOWY_BUSH_MEDIUM, "Ośnieżony krzak");
        obj.setGrubbingType(GrubbingType.DIGGING);
        obj.setEnergyToGrubb(20);
        obj.addGrubbingProfit(new GrubbingProfit(
                GameplayService.getInstance().getStorageItemDescriptorsManager().getByType(StorageItemTypes.BUSH_WOOD),
                4));
        obj.addGrubbingProfit(new GrubbingProfit(
                GameplayService.getInstance().getStorageItemDescriptorsManager().getByType(StorageItemTypes.ICE), 1));

        return obj;
    }

    private ObjectTypeDescriptor createFragaria()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.FRAGARIA, "Poziomka");
        obj.setGrubbingType(GrubbingType.DIGGING);
        obj.setEnergyToGrubb(10);
        obj.addGrubbingProfit(new GrubbingProfit(
                GameplayService.getInstance().getStorageItemDescriptorsManager().getByType(StorageItemTypes.FRAGARIA),
                4));

        return obj;
    }

    private ObjectTypeDescriptor createRubus()
    {
        ObjectTypeDescriptor obj = new ObjectTypeDescriptor(ObjectTypes.RUBUS, "Jeżyna");
        obj.setGrubbingType(GrubbingType.DIGGING);
        obj.setEnergyToGrubb(10);
        obj.addGrubbingProfit(new GrubbingProfit(
                GameplayService.getInstance().getStorageItemDescriptorsManager().getByType(StorageItemTypes.RUBUS), 4));

        return obj;
    }

    private ObjectTypeDescriptor createBarn()
    {
        return new ObjectTypeDescriptor(ObjectTypes.BARN, "Stodoła");
    }
}
