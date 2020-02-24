package com.github.wmarkow.klondiklon.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TexturesRegistrar
{
    public final static String LIGHTNING = "LIGHTNING";
    public final static String BALLOON = "BALLOON";
    public final static String BALLOON_BLUE = "BALLOON_BLUE";
    public final static String BACKPACK = "BACKPACK";
    public final static String WAREHOUSE_BACKGROUND = "WAREHOUSE_BACKGROUND";
    public final static String WAREHOUSE_ITEM_BACKGROUND = "WAREHOUSE_ITEM_BACKGROUND";

    public final static String STORAGE_ITEM_ICE = "STORAGE_ITEM_ICE";
    public final static String STORAGE_ITEM_STONE = "STORAGE_ITEM_STONE";
    public final static String STORAGE_ITEM_WOOD = "STORAGE_ITEM_WOOD";
    public final static String STORAGE_ITEM_FIR_WOOD = "STORAGE_ITEM_FIR_WOOD";
    public final static String STORAGE_ITEM_BUSH_WOOD = "STORAGE_ITEM_BUSH_WOOD";
    public final static String STORAGE_ITEM_GRASS = "STORAGE_ITEM_GRASS";
    public final static String STORAGE_ITEM_FRAGARIA = "STORAGE_ITEM_FRAGARIA";
    public final static String STORAGE_ITEM_RUBUS = "STORAGE_ITEM_RUBUS";
    public final static String STORAGE_ITEM_COAL = "STORAGE_ITEM_COAL";

    public void register(TexturesManager texturesManager)
    {
        texturesManager.registerTexture(LIGHTNING, new Texture(Gdx.files.classpath("ui/lightning.png")));
        texturesManager.registerTexture(BALLOON, new Texture(Gdx.files.classpath("ui/balloon.png")));
        texturesManager.registerTexture(BALLOON_BLUE, new Texture(Gdx.files.classpath("ui/balloon_blue.png")));
        texturesManager.registerTexture(BACKPACK, new Texture(Gdx.files.classpath("ui/backpack.png")));
        texturesManager.registerTexture(WAREHOUSE_BACKGROUND,
                new Texture(Gdx.files.classpath("ui/warehouse_background.png")));
        texturesManager.registerTexture(WAREHOUSE_ITEM_BACKGROUND,
                new Texture(Gdx.files.classpath("ui/warehouse_item_background.png")));

        texturesManager.registerTexture(STORAGE_ITEM_ICE,
                new Texture(Gdx.files.classpath("worlds/home/warehouse/items/ice.png")));
        texturesManager.registerTexture(STORAGE_ITEM_STONE,
                new Texture(Gdx.files.classpath("worlds/home/warehouse/items/stone.png")));
        texturesManager.registerTexture(STORAGE_ITEM_WOOD,
                new Texture(Gdx.files.classpath("worlds/home/warehouse/items/wood.png")));
        texturesManager.registerTexture(STORAGE_ITEM_FIR_WOOD,
                new Texture(Gdx.files.classpath("worlds/home/warehouse/items/wood_fir.png")));
        texturesManager.registerTexture(STORAGE_ITEM_BUSH_WOOD,
                new Texture(Gdx.files.classpath("worlds/home/warehouse/items/wood_bush.png")));
        texturesManager.registerTexture(STORAGE_ITEM_GRASS,
                new Texture(Gdx.files.classpath("worlds/home/warehouse/items/grass.png")));
        texturesManager.registerTexture(STORAGE_ITEM_FRAGARIA,
                new Texture(Gdx.files.classpath("worlds/home/warehouse/items/fragaria.png")));
        texturesManager.registerTexture(STORAGE_ITEM_RUBUS,
                new Texture(Gdx.files.classpath("worlds/home/warehouse/items/rubus.png")));
        texturesManager.registerTexture(STORAGE_ITEM_COAL,
                new Texture(Gdx.files.classpath("worlds/home/warehouse/items/coal.png")));
    }
}
