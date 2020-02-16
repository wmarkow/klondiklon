package com.github.wmarkow.klondiklon.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TexturesManager
{
    public Texture LIGHTNING;
    public Texture BALLOON;
    public Texture BACKPACK;

    public void init()
    {
        LIGHTNING = new Texture(Gdx.files.internal("images/lightning.png"));
        BALLOON = new Texture(Gdx.files.internal("images/balloon.png"));
        BACKPACK = new Texture(Gdx.files.internal("images/backpack.png"));
    }
}
