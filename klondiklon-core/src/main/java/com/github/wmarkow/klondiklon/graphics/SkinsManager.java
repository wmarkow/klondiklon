package com.github.wmarkow.klondiklon.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinsManager
{
    public Skin GLASSY;

    public void init()
    {
        GLASSY = new Skin(Gdx.files.internal("skins/glassy/skin/glassy-ui.json"));
    }
}
