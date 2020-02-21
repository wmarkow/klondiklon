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
        texturesManager.registerTexture(LIGHTNING, new Texture(Gdx.files.internal("images/lightning.png")));
        texturesManager.registerTexture(BALLOON, new Texture(Gdx.files.internal("images/balloon.png")));
        texturesManager.registerTexture(BALLOON_BLUE, new Texture(Gdx.files.internal("images/balloon_blue.png")));
        texturesManager.registerTexture(BACKPACK, new Texture(Gdx.files.internal("images/backpack.png")));

        texturesManager.registerTexture(STORAGE_ITEM_ICE, new Texture(Gdx.files.internal("images/items/ice.png")));
        texturesManager.registerTexture(STORAGE_ITEM_STONE, new Texture(Gdx.files.internal("images/items/stone.png")));
        texturesManager.registerTexture(STORAGE_ITEM_WOOD, new Texture(Gdx.files.internal("images/items/wood.png")));
        texturesManager.registerTexture(STORAGE_ITEM_FIR_WOOD,
                new Texture(Gdx.files.internal("images/items/wood_fir.png")));
        texturesManager.registerTexture(STORAGE_ITEM_BUSH_WOOD,
                new Texture(Gdx.files.internal("images/items/wood_bush.png")));
        texturesManager.registerTexture(STORAGE_ITEM_GRASS, new Texture(Gdx.files.internal("images/items/grass.png")));
        texturesManager.registerTexture(STORAGE_ITEM_FRAGARIA,
                new Texture(Gdx.files.internal("images/items/fragaria.png")));
        texturesManager.registerTexture(STORAGE_ITEM_RUBUS, new Texture(Gdx.files.internal("images/items/rubus.png")));
        texturesManager.registerTexture(STORAGE_ITEM_COAL,
                new Texture(Gdx.files.internal("images/items/coal.png")));

        texturesManager.registerTexture(WAREHOUSE_BACKGROUND,
                new Texture(Gdx.files.internal("images/warehouse_background.png")));
        texturesManager.registerTexture(WAREHOUSE_ITEM_BACKGROUND,
                new Texture(Gdx.files.internal("images/warehouse_item_background.png")));
    }
}
