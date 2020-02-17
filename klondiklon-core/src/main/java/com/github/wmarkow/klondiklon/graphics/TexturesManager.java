package com.github.wmarkow.klondiklon.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TexturesManager
{
    public Texture LIGHTNING;
    public Texture BALLOON;
    public Texture BACKPACK;

    public Texture STORAGE_ITEM_ICE;
    public Texture STORAGE_ITEM_STONE;
    public Texture STORAGE_ITEM_WOOD;
    public Texture STORAGE_ITEM_FIR_WOOD;
    public Texture STORAGE_ITEM_BUSH_WOOD;
    public Texture STORAGE_ITEM_GRASS;

    public void init()
    {
        LIGHTNING = new Texture(Gdx.files.internal("images/lightning.png"));
        BALLOON = new Texture(Gdx.files.internal("images/balloon.png"));
        BACKPACK = new Texture(Gdx.files.internal("images/backpack.png"));

        STORAGE_ITEM_ICE = new Texture(Gdx.files.internal("images/items/ice.png"));
        STORAGE_ITEM_STONE = new Texture(Gdx.files.internal("images/items/stone.png"));
        STORAGE_ITEM_WOOD = new Texture(Gdx.files.internal("images/items/wood.png"));
        STORAGE_ITEM_FIR_WOOD = new Texture(Gdx.files.internal("images/items/wood_fir.png"));
        STORAGE_ITEM_BUSH_WOOD = new Texture(Gdx.files.internal("images/items/wood_bush.png"));
        STORAGE_ITEM_GRASS = new Texture(Gdx.files.internal("images/items/grass.png"));
    }
}
