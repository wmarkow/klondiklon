package com.github.wmarkow.klondiklon.resources.graphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinsManager
{
    public final static String GLASSY = "GLASSY";

    private Map<String, Skin> skins = new HashMap<String, Skin>();

    public void init()
    {
        registerSkin(GLASSY, new Skin(Gdx.files.classpath("skins/glassy/skin/glassy-ui.json")));
    }

    public void registerSkin(String name, Skin skin)
    {
        skins.put(name, skin);
    }

    public Skin getSkin(String name)
    {
        return skins.get(name);
    }
}
