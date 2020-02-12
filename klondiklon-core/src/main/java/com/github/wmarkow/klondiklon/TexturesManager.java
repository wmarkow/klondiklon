package com.github.wmarkow.klondiklon;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TexturesManager
{
    public final static String LIGHTNING = "images/lightning.png";
    public final static String BALLOON = "images/balloon.png";

    private Map<String, Texture> textures = new HashMap<String, Texture>();

    public Texture get(String name)
    {
        return textures.get(name);
    }

    public void load()
    {
        textures.put(LIGHTNING, new Texture(Gdx.files.internal(LIGHTNING)));
        textures.put(BALLOON, new Texture(Gdx.files.internal(BALLOON)));
    }
}
